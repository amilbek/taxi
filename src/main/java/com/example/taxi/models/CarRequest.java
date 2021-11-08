package com.example.taxi.models;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class CarRequest {
    private Integer id;
    private String carNumber;
    private String carModel;
    private String carColor;
    private String tariff;
}
