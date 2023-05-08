package ro.msg.learning.shop.controller.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@Data
@RequiredArgsConstructor
public class StockDTO {

    private String name;
    private String description;
    private BigDecimal price;
    private Double weight;
    private String category;
    private String supplier;
    private String imageUrl;
    private Integer quantity;
    private String countryAddress;
    private String cityAddress;
    private String provinceAddress;
    private String streetAddress;
}
