package ro.msg.learning.shop.service.order;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ro.msg.learning.shop.controller.dto.CreateOrderDTO;
import ro.msg.learning.shop.domain.entity.Location;
import ro.msg.learning.shop.domain.entity.Order;
import ro.msg.learning.shop.domain.entity.OrderDetail;
import ro.msg.learning.shop.domain.entity.OrderDetailKey;
import ro.msg.learning.shop.domain.entity.Product;
import ro.msg.learning.shop.domain.entity.Revenue;
import ro.msg.learning.shop.domain.entity.Stock;
import ro.msg.learning.shop.domain.entity.StockKey;
import ro.msg.learning.shop.domain.repository.OrderDetailRepository;
import ro.msg.learning.shop.domain.repository.OrderRepository;
import ro.msg.learning.shop.domain.repository.RevenueRepository;
import ro.msg.learning.shop.domain.repository.StockRepository;
import ro.msg.learning.shop.exception.stock.OutOfStockException;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderServiceMostAbundantLocationTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private OrderDetailRepository orderDetailRepository;

    @Mock
    private StockRepository stockRepository;

    @Mock
    private RevenueRepository revenueRepository;

    @InjectMocks
    private OrderServiceMostAbundantImpl underTest;

    @SneakyThrows
    @Test
    void handleOrderForMostAbundantLocation() {
        //given
        //location stock 1
        Location location = new Location();
        location.setId(1);
        location.setCountryAddress("Country location test");
        location.setCityAddress("City location test");
        location.setProvinceAddress("Province location Test");
        location.setStreetAddress("Street location Test");
        //location stock 2
        Location location2 = new Location();
        location2.setId(2);
        location2.setCountryAddress("Country location2 test");
        location2.setCityAddress("City location2 test");
        location2.setProvinceAddress("Province location2 Test");
        location2.setStreetAddress("Street location2 Test");
        //location destination
        Location locationDestination = new Location();
        locationDestination.setCountryAddress("destination country");
        locationDestination.setCityAddress("destination city");
        locationDestination.setProvinceAddress("destination province");
        locationDestination.setStreetAddress("destination street");

        //product
        Product product = new Product();
        product.setId(1);
        product.setPrice(BigDecimal.valueOf(100.00));
        //stock 1
        StockKey stockKey = new StockKey();
        stockKey.setProductId(1);
        stockKey.setLocationId(1);
        Stock stock = new Stock();
        stock.setId(stockKey);
        stock.setLocation(location);
        stock.setQuantity(10);
        stock.setProduct(product);
        //stock 2
        StockKey stockKey2 = new StockKey();
        stockKey2.setProductId(1);
        stockKey2.setLocationId(2);
        Stock stock2 = new Stock();
        stock2.setId(stockKey);
        stock2.setLocation(location2);
        stock2.setQuantity(20);
        stock2.setProduct(product);

        //stocks
        Set<Stock> stockList = Set.of(stock,stock2);

        //order
        Order order = new Order();
        order.setCountryAddress(locationDestination.getCountryAddress());
        order.setCityAddress(locationDestination.getCityAddress());
        order.setProvinceAddress(locationDestination.getProvinceAddress());
        order.setStreetAddress(locationDestination.getStreetAddress());
        order.setShippedFrom(location2);
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        order.setCreatedAt(timestamp.toLocalDateTime());

        //orderDetail
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setQuantity(1);
        orderDetail.setProduct(stock2.getProduct());
        orderDetail.setId(new OrderDetailKey(null,1));
        orderDetail.setOrder(order);

        //revenue
        Revenue revenue = new Revenue();
        revenue.setLocalDate(timestamp);
        revenue.setLocation(locationDestination);
        revenue.setSum(BigDecimal.valueOf(100.00));

        //when
        when(stockRepository.findAllStockByProductId(any())).thenReturn(stockList);
        when(orderRepository.save(any())).thenReturn(order);

        CreateOrderDTO createOrderDTO = new CreateOrderDTO();
        createOrderDTO.setTimestamp(timestamp);
        createOrderDTO.setCountryAddress(locationDestination.getCountryAddress());
        createOrderDTO.setCityAddress(locationDestination.getCityAddress());
        createOrderDTO.setProvinceAddress(locationDestination.getProvinceAddress());
        createOrderDTO.setStreetAddress(locationDestination.getStreetAddress());
        createOrderDTO.setProducts(new HashMap<>(){{
            put(1,1);
        }});
        underTest.handleOrder(createOrderDTO);

        //then
        //capture order
        ArgumentCaptor<Order> orderCaptor = ArgumentCaptor.forClass(Order.class);
        verify(orderRepository).save(orderCaptor.capture());
        Order capturedOrder = orderCaptor.getValue();
        assertThat(order).isEqualTo(capturedOrder);

        //capture orderDetail set
        ArgumentCaptor<Set<OrderDetail>> orderDetailCaptor = ArgumentCaptor.forClass(Set.class);
        verify(orderDetailRepository).saveAll(orderDetailCaptor.capture());
        Set<OrderDetail> captureOrderDetailsSet = orderDetailCaptor.getValue();
        OrderDetail caputuredOrderDetail = captureOrderDetailsSet.stream().findFirst().get();
        assertThat(orderDetail).isEqualTo(caputuredOrderDetail);

        //capture revenue
        ArgumentCaptor<Revenue> revenueCaptor = ArgumentCaptor.forClass(Revenue.class);
        verify(revenueRepository).save(revenueCaptor.capture());
        Revenue capturedRevenue = revenueCaptor.getValue();
        assertThat(revenue).isEqualTo(capturedRevenue);
    }

    @SneakyThrows
    @Test
    void handleOutOfStockOrderForMostAbundantLocation() {
        //given
        //location stock 1
        Location location = new Location();
        location.setId(1);
        location.setCountryAddress("Country location test");
        location.setCityAddress("City location test");
        location.setProvinceAddress("Province location Test");
        location.setStreetAddress("Street location Test");

        //location destination
        Location locationDestination = new Location();
        locationDestination.setCountryAddress("destination country");
        locationDestination.setCityAddress("destination city");
        locationDestination.setProvinceAddress("destination province");
        locationDestination.setStreetAddress("destination street");

        //product
        Product product = new Product();
        product.setId(1);
        product.setPrice(BigDecimal.valueOf(100.00));
        //stock 1
        StockKey stockKey = new StockKey();
        stockKey.setProductId(1);
        stockKey.setLocationId(1);
        Stock stock = new Stock();
        stock.setId(stockKey);
        stock.setLocation(location);
        stock.setQuantity(10);
        stock.setProduct(product);

        //stocks
        Set<Stock> stockList = Set.of(stock);

        //when
        when(stockRepository.findAllStockByProductId(any())).thenReturn(stockList);

        CreateOrderDTO createOrderDTO = new CreateOrderDTO();
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        createOrderDTO.setTimestamp(timestamp);
        createOrderDTO.setCountryAddress(locationDestination.getCountryAddress());
        createOrderDTO.setCityAddress(locationDestination.getCityAddress());
        createOrderDTO.setProvinceAddress(locationDestination.getProvinceAddress());
        createOrderDTO.setStreetAddress(locationDestination.getStreetAddress());
        createOrderDTO.setProducts(new HashMap<>(){{
            put(1,500);
        }});
        //then
        assertThatThrownBy(() -> underTest.handleOrder(createOrderDTO))
                .isInstanceOf(OutOfStockException.class)
                .hasMessageContaining("order demand is to high for actual stock");
    }
}