package com.example.taxi.models;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class OrderRequest {
    private Integer id;
    private String addressFrom;
    private String addressTo;
    private String tariff;
}
