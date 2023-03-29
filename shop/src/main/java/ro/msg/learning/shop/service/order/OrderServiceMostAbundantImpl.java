package ro.msg.learning.shop.service.order;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import ro.msg.learning.shop.controller.dto.CreateOrderDTO;
import ro.msg.learning.shop.domain.entity.Location;
import ro.msg.learning.shop.domain.entity.Order;
import ro.msg.learning.shop.domain.entity.OrderDetail;
import ro.msg.learning.shop.domain.entity.OrderDetailKey;
import ro.msg.learning.shop.domain.entity.Revenue;
import ro.msg.learning.shop.domain.entity.Stock;
import ro.msg.learning.shop.domain.repository.OrderDetailRepository;
import ro.msg.learning.shop.domain.repository.OrderRepository;
import ro.msg.learning.shop.domain.repository.RevenueRepository;
import ro.msg.learning.shop.domain.repository.StockRepository;
import ro.msg.learning.shop.exception.ShopAppException;
import ro.msg.learning.shop.exception.stock.OutOfStockException;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
@Slf4j
@AllArgsConstructor
@ConditionalOnProperty(name="order.service.implementation.strategy",havingValue = "most-abundant")
public class OrderServiceMostAbundantImpl implements OrderService{

    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final StockRepository stockRepository;
    private final RevenueRepository revenueRepository;

    @Override
    public Order handleOrder(CreateOrderDTO orderInput) throws ShopAppException {

        Map<Integer, Integer> productsMap = orderInput.getProducts();
        Order order = new Order();
        Revenue revenue = new Revenue();
        revenue.setSum(BigDecimal.ZERO);

        //get stocks
        Set<Stock> stocks = productsMap.keySet()
                .stream()
                .map( id -> {
                            Optional<Stock> maxStock = stockRepository.findAllStockByProductId(id).stream()
                                    .max(Comparator.comparing(Stock::getQuantity));
                            return maxStock.orElse(null);
                        }
                ).collect(Collectors.toSet());

        //check stocks
        if(stocks.stream().anyMatch(stock -> stock.getQuantity() < productsMap.get(stock.getProduct().getId()))){
            throw new OutOfStockException();
        }

        //set order details, new stock quantity and revenue sum
        Set<OrderDetail> newOrderDetailSet = new HashSet<>();
        stocks.forEach(stock -> {
            Integer orderedQuantity = productsMap.get(stock.getProduct().getId());
            Integer newQuantity = stock.getQuantity() - orderedQuantity;
            stock.setQuantity(newQuantity);

            BigDecimal actualSum = revenue.getSum();
            revenue.setSum(
                    BigDecimal.valueOf(orderedQuantity)
                            .multiply(stock.getProduct().getPrice())
                            .add(actualSum));

            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setQuantity(orderedQuantity);
            orderDetail.setProduct(stock.getProduct());
            newOrderDetailSet.add(orderDetail);
        });


        //set order fields
        stocks.stream().findFirst().ifPresent(stock -> order.setShippedFrom(stock.getLocation()));
        order.setCreatedAt(orderInput.getTimestamp().toLocalDateTime());
        order.setCountryAddress(orderInput.getCountryAddress());
        order.setCityAddress(orderInput.getCityAddress());
        order.setProvinceAddress(orderInput.getProvinceAddress());
        order.setStreetAddress(orderInput.getStreetAddress());
        Order responseOrder = orderRepository.save(order);

        //save order details
        Set<OrderDetail> saveOrderDetails = newOrderDetailSet.stream()
                .map(orderDetail -> {
                    orderDetail.setId(new OrderDetailKey(responseOrder.getId(), orderDetail.getProduct().getId()));
                    orderDetail.setOrder(responseOrder);
                    return orderDetail;
                }).collect(Collectors.toSet());

        orderDetailRepository.saveAll(saveOrderDetails);

        //save revenue
        Location location = new Location();
        location.setCountryAddress(orderInput.getCountryAddress());
        location.setCityAddress(orderInput.getCityAddress());
        location.setProvinceAddress(orderInput.getProvinceAddress());
        location.setStreetAddress(orderInput.getStreetAddress());
        revenue.setLocalDate(orderInput.getTimestamp());
        revenue.setLocation(location);
        revenueRepository.save(revenue);

        return responseOrder;
    }
}
