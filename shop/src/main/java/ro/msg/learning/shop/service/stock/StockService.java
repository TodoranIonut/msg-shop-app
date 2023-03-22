package ro.msg.learning.shop.service.stock;

import ro.msg.learning.shop.domain.entity.Stock;

import java.util.List;

public interface StockService {

    List<Stock> getStocksByLocationId(Integer locationId);
}
