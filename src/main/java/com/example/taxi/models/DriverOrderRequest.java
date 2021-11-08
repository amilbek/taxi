package com.example.taxi.models;

import lombok.Data;

@Data
public class DriverOrderRequest {
    private Integer driverId;
    private Integer orderId;
}
