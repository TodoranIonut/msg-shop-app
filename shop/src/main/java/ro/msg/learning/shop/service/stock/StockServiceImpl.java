package ro.msg.learning.shop.service.stock;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ro.msg.learning.shop.domain.entity.Stock;
import ro.msg.learning.shop.domain.repository.StockRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class StockServiceImpl implements StockService{

    private final StockRepository stockRepository;

    @Override
    public List<Stock> getStocksByLocationId(Integer locationId) {
        return stockRepository.findAllStockByLocationId(locationId);
    }
}
