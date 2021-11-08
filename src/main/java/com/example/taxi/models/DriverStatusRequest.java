package com.example.taxi.models;

import lombok.Data;

@Data
public class DriverStatusRequest {
    private Integer driverId;
    private Boolean driverStatus;
}
