package com.example.taxi.models;

import lombok.Data;
import lombok.ToString;

import java.sql.Date;

@Data
@ToString
public class DriverRequest {
    private Integer id;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String driverIdNumber;
    private String driverLicenseNumber;
    private Date licenseExpDate;
    private Double rating;
    private String username;
    private String password;
}
