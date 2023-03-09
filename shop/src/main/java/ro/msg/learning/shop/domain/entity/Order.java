package ro.msg.learning.shop.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    private Location shippedFrom;

    @ManyToOne
    private Customer customer;

    private LocalDateTime createdAt;
    private String countryAddress;
    private String cityAddress;
    private String provinceAddress;
    private String streetAddress;
}
