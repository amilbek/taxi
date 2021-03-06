package com.example.taxi.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name="orders")
@Getter
@Setter
@NoArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String addressFrom;
    private String addressTo;
    private String orderStartTime;
    private String orderEndTime;
    private Double orderDistance;
    private String tariff;
    private Double orderPrice;
    private String orderStatus;

    @OneToOne(targetEntity=User.class,cascade=CascadeType.ALL)
    private User user;

    @OneToOne(targetEntity=Driver.class,cascade=CascadeType.ALL)
    private Driver driver;

    public Order(String addressFrom, String addressTo,
                 Double orderDistance, String tariff, Double orderPrice) {
        this.addressFrom = addressFrom;
        this.addressTo = addressTo;
        this.orderDistance = orderDistance;
        this.tariff = tariff;
        this.orderPrice = orderPrice;
        this.orderStatus = "ordered";
    }
}
