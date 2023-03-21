package ro.msg.learning.shop.controller.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductDTO {

    private Integer id;
    private String name;
    private String description;
    private BigDecimal price;
    private Double weight;
    private Integer categoryId;
    private Integer supplierId;
    private String imageUrl;
}
