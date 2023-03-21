package ro.msg.learning.shop.service.order;

import ro.msg.learning.shop.controller.dto.CreateOrderDTO;
import ro.msg.learning.shop.domain.entity.Order;
import ro.msg.learning.shop.exception.ShopAppException;

public interface OrderService {

    Order handleOrder(CreateOrderDTO orderInput) throws ShopAppException;
}
