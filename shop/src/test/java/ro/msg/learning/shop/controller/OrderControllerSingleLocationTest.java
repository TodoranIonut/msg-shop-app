package ro.msg.learning.shop.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import lombok.SneakyThrows;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import ro.msg.learning.shop.controller.dto.AuthenticationRequestDTO;
import ro.msg.learning.shop.controller.dto.CreateOrderDTO;
import ro.msg.learning.shop.domain.entity.Stock;
import ro.msg.learning.shop.domain.repository.ProductRepository;
import ro.msg.learning.shop.domain.repository.StockRepository;

import java.sql.Timestamp;
import java.util.HashMap;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles("test")
@TestPropertySource(
        locations = "/application-test.properties",
        properties = {
                "order.service.implementation.strategy=single-location",
                "security.login.application=with-jwt"
        }
)
class OrderControllerSingleLocationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private StockRepository stockRepository;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void resetDataBase(@Autowired Flyway flyway) {
        flyway.clean();
        flyway.migrate();
    }

    @SneakyThrows
    private String getAuthToken() {
        AuthenticationRequestDTO authenticationRequestDTO = new AuthenticationRequestDTO();
        authenticationRequestDTO.setUsername("rogers");
        authenticationRequestDTO.setPassword("123");
        String requestBody = objectMapper.writeValueAsString(authenticationRequestDTO);

        MvcResult resultToken = mockMvc.perform(post("/api/v1/auth")
                .content(requestBody)
                .contentType(APPLICATION_JSON)
        ).andExpect(status().isOk()).andReturn();

        String responseContent = resultToken.getResponse().getContentAsString();
        JsonNode jsonNode = objectMapper.readTree(responseContent).get("token");
        return "Bearer " + jsonNode.textValue();
    }

    @SneakyThrows
    @Test
    void createOrderTest() {
        CreateOrderDTO createOrderDTO = new CreateOrderDTO();
        createOrderDTO.setTimestamp(new Timestamp(System.currentTimeMillis()));
        createOrderDTO.setCountryAddress("country test");
        createOrderDTO.setCityAddress("city test");
        createOrderDTO.setProvinceAddress("province test");
        createOrderDTO.setStreetAddress("address test");
        Integer productId = 1;
        createOrderDTO.setProducts(new HashMap<>() {{
            put(productId, 1);
        }});

        String tokenString = getAuthToken();
        Stock stock = stockRepository.findFirstStockByProductId(productId).orElse(null);
        String requestBody = objectMapper.writeValueAsString(createOrderDTO);
        mockMvc.perform(post("/api/v1/order/create")
                        .content(requestBody)
                        .header("authorization", tokenString)
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$[0].countryAddress").value(createOrderDTO.getCountryAddress()))
                .andExpect(jsonPath("$[0].cityAddress").value(createOrderDTO.getCityAddress()))
                .andExpect(jsonPath("$[0].provinceAddress").value(createOrderDTO.getProvinceAddress()))
                .andExpect(jsonPath("$[0].streetAddress").value(createOrderDTO.getStreetAddress()))
                .andExpect(jsonPath("$[0].shippedFrom.countryAddress").value(stock.getLocation().getCountryAddress()))
                .andExpect(jsonPath("$[0].shippedFrom.cityAddress").value(stock.getLocation().getCityAddress()))
                .andExpect(jsonPath("$[0].shippedFrom.provinceAddress").value(stock.getLocation().getProvinceAddress()))
                .andExpect(jsonPath("$[0].shippedFrom.streetAddress").value(stock.getLocation().getStreetAddress()));
    }

    @SneakyThrows
    @Test
    void createOrderWithMissingStock() {
        CreateOrderDTO createOrderDTO = new CreateOrderDTO();
        createOrderDTO.setTimestamp(new Timestamp(System.currentTimeMillis()));
        createOrderDTO.setCountryAddress("country test");
        createOrderDTO.setCityAddress("city test");
        createOrderDTO.setProvinceAddress("province test");
        createOrderDTO.setStreetAddress("address test");

        Integer productId = 1;
        Stock stock = stockRepository.findFirstStockByProductId(productId).orElse(null);
        Integer moreThenAvailableQuantity = stock.getQuantity() + 1;

        createOrderDTO.setProducts(new HashMap<>() {{
            put(productId, moreThenAvailableQuantity);
        }});

        String tokenString = getAuthToken();
        String requestBody = objectMapper.writeValueAsString(createOrderDTO);
        mockMvc.perform(post("/api/v1/order/create")
                        .content(requestBody)
                        .header("authorization", tokenString)
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("order demand is to high for actual stock"));
    }
}