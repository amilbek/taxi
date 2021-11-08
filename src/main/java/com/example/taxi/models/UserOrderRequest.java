package com.example.taxi.models;

import lombok.Data;

@Data
public class UserOrderRequest {
    private Integer userId;
    private Integer orderId;
}