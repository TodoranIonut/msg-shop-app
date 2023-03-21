package ro.msg.learning.shop.controller.dto;

import lombok.Data;

import java.sql.Timestamp;
import java.util.Map;

@Data
public class CreateOrderDTO {

    private Timestamp timestamp;
    private String countryAddress;
    private String cityAddress;
    private String provinceAddress;
    private String streetAddress;
    private Map<Integer,Integer> products;
}
