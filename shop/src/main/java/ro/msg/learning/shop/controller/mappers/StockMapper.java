package ro.msg.learning.shop.controller.mappers;

import org.springframework.stereotype.Component;
import ro.msg.learning.shop.controller.dto.StockDTO;
import ro.msg.learning.shop.domain.entity.Location;
import ro.msg.learning.shop.domain.entity.Product;
import ro.msg.learning.shop.domain.entity.Stock;

import java.util.Collections;
import java.util.List;

@Component
public class StockMapper {

    public StockDTO toStockDTO(Stock stock){
        if(stock == null){
            return null;
        }

        StockDTO stockDTO = new StockDTO();
        Product product = stock.getProduct();
        Location location = stock.getLocation();

        stockDTO.setName(product.getName());
        stockDTO.setDescription(product.getDescription());
        stockDTO.setPrice(product.getPrice());
        stockDTO.setWeight(product.getWeight());
        stockDTO.setCategory(product.getCategory().getName());
        stockDTO.setSupplier(product.getSupplier().getName());
        stockDTO.setImageUrl(product.getImageUrl());
        stockDTO.setQuantity(stock.getQuantity());
        stockDTO.setCountryAddress(location.getCountryAddress());
        stockDTO.setCityAddress(location.getCityAddress());
        stockDTO.setProvinceAddress(location.getProvinceAddress());
        stockDTO.setStreetAddress(location.getStreetAddress());

        return stockDTO;
    }

    public List<StockDTO> toStoksDTOList(List<Stock> stocks) {

        if (stocks.isEmpty()) {
            return Collections.emptyList();
        }

        return stocks.stream()
                .map(this::toStockDTO)
                .toList();
    }
}
