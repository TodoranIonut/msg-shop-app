package ro.msg.learning.shop.controller;

import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import ro.msg.learning.shop.controller.mappers.ProductMapper;
import ro.msg.learning.shop.domain.entity.Product;
import ro.msg.learning.shop.service.product.ProductService;

import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@RunWith(SpringRunner.class)
//@WebMvcTest(OrderController.class)
@WebMvcTest(ProductController.class)
//@ActiveProfiles("test")
class OrderControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

//    @Mock
//    private OrderService orderService;

    @MockBean
    private ProductService productService;

    private ProductMapper productMapper;

    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @AfterEach
    void tearDown() {
    }

    @SneakyThrows
    @Test
    void createOrder() {

//        CreateOrderDTO createOrderDTO = new CreateOrderDTO();
//        underTest.createOrder(createOrderDTO);

        Product product = new Product();
        product.setId(2);
        List<Product> productList = List.of(product);
        given(productService.getAllProducts()).willReturn(productList);

        mockMvc.perform(get("/api/v1/product/all")
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}