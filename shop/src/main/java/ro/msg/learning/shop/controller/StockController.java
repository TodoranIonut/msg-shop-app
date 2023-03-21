package ro.msg.learning.shop.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ro.msg.learning.shop.controller.dto.StockDTO;
import ro.msg.learning.shop.controller.mappers.StockMapper;
import ro.msg.learning.shop.domain.entity.Stock;
import ro.msg.learning.shop.service.stock.StockService;

import java.util.List;


@RestController
@RequestMapping("/api/v1/stock")
@AllArgsConstructor
public class StockController {

    private final StockService stockService;
    private final StockMapper stockMapper;

    @GetMapping(value = "/csv/{locationId}",produces ="text/csv")
    public ResponseEntity<List<StockDTO>> exportStockAsCSV(@PathVariable Integer locationId){
        List<Stock> stocks = stockService.getStocksByLocationId(locationId);
        List<StockDTO> myDataList = stockMapper.toStoksDTOList(stocks);
        return ResponseEntity.ok().body(myDataList);
    }
}
