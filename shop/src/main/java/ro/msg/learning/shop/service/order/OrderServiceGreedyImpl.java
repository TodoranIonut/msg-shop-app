package ro.msg.learning.shop.service.order;

import com.fasterxml.jackson.core.JsonProcessingException;
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
import ro.msg.learning.shop.exception.product.ProductNotFoundException;
import ro.msg.learning.shop.exception.stock.OutOfStockException;
import ro.msg.learning.shop.exception.type.BadRequestException;
import ro.msg.learning.shop.service.mapQuest.MapQuestService;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
@Slf4j
@AllArgsConstructor
@ConditionalOnProperty(name = "order.service.implementation.strategy", havingValue = "greedy")
public class OrderServiceGreedyImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final StockRepository stockRepository;
    private final RevenueRepository revenueRepository;
    private final MapQuestService mapQuestService;

    @Override
    public Set<Order> handleOrder(CreateOrderDTO orderInput) throws ShopAppException {

        Map<Integer, Integer> productsMap = orderInput.getProducts();
        Set<Order> responseOrderList = new HashSet<>();
        Revenue revenue = new Revenue();
        revenue.setSum(BigDecimal.ZERO);

        //check all product ids
        for (Integer id : productsMap.keySet()) {
            if (!stockRepository.existsStockByProductId(id))
                throw new ProductNotFoundException(id);
        }

        //get all stocks for each product
        Map<Integer, Set<Stock>> allProductStocks = new HashMap<>();
        for (Integer id : productsMap.keySet()) {
            Set<Stock> getStocks = stockRepository.findAllStockByProductId(id);
            allProductStocks.put(id, getStocks);
        }

        for (Map.Entry<Integer, Set<Stock>> entry : allProductStocks.entrySet()) {
            Integer productId = entry.getKey();
            Set<Stock> thisProductStocks = allProductStocks.get(productId);

            //check stocks for productId
            if (thisProductStocks.stream().mapToInt(Stock::getQuantity).sum() < productsMap.get(productId)) {
                throw new OutOfStockException();
            }

            //sort stocks for productId by distance
            List<Stock> sortedStocks;
            try {
                String shippedToLocation = orderInput.getCityAddress() + ", " + orderInput.getProvinceAddress();
                sortedStocks = mapQuestService.getSortedByDistanceLocations(thisProductStocks.toArray(new Stock[0]), shippedToLocation);
            } catch (JsonProcessingException e){
                throw new BadRequestException("Getting stock by distance cannot perform because of JSON body for MapQuest request");
            }


            //set order details, new stock quantity and revenue sum
            Set<OrderDetail> newOrderDetailSet = new HashSet<>();
            Integer requestedQuantity = productsMap.get(productId);
            for (Stock s : sortedStocks) {
               Integer forPayQuantity;
                if (requestedQuantity > 0) {
                    Integer currentStockQuantity = s.getQuantity();
                    if (currentStockQuantity > requestedQuantity) {
                        Integer newQuantity = currentStockQuantity - requestedQuantity;
                        forPayQuantity = requestedQuantity;
                        s.setQuantity(newQuantity);
                        requestedQuantity = 0;
                    } else {
                        forPayQuantity = currentStockQuantity;
                        requestedQuantity = requestedQuantity - currentStockQuantity;
                        s.setQuantity(0);
                    }

                    BigDecimal actualSum = revenue.getSum();
                    revenue.setSum(
                            BigDecimal.valueOf(forPayQuantity)
                                    .multiply(s.getProduct().getPrice())
                                    .add(actualSum));

                    OrderDetail orderDetail = new OrderDetail();
                    orderDetail.setQuantity(forPayQuantity);
                    orderDetail.setProduct(s.getProduct());
                    newOrderDetailSet.add(orderDetail);
                } else break;
            }

            Order order = new Order();
            //set order fields
            thisProductStocks.stream().findFirst().ifPresent(stock -> order.setShippedFrom(stock.getLocation()));
            order.setCreatedAt(orderInput.getTimestamp().toLocalDateTime());
            order.setCountryAddress(orderInput.getCountryAddress());
            order.setCityAddress(orderInput.getCityAddress());
            order.setProvinceAddress(orderInput.getProvinceAddress());
            order.setStreetAddress(orderInput.getStreetAddress());
            Order responseOrder = orderRepository.save(order);
            responseOrderList.add(responseOrder);

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
        }
        return responseOrderList;
    }
}
