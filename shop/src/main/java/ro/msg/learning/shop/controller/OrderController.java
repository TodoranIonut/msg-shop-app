package ro.msg.learning.shop.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ro.msg.learning.shop.controller.dto.CreateOrderDTO;
import ro.msg.learning.shop.domain.entity.Order;
import ro.msg.learning.shop.exception.ShopAppException;
import ro.msg.learning.shop.service.order.OrderService;

import java.net.URI;
import java.util.Set;

@AllArgsConstructor
@RestController
@RequestMapping(value = "/api/v1/order" ,produces = MediaType.APPLICATION_JSON_VALUE)
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/create")
    public ResponseEntity<Set<Order>> createOrder(@RequestBody CreateOrderDTO orderInput) throws ShopAppException {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/v1/order/create").toUriString());
        Set<Order> order = orderService.handleOrder(orderInput);
        return ResponseEntity.created(uri).body(order);
    }
}
