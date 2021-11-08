package com.example.taxi.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name="drivers")
@Getter
@Setter
@NoArgsConstructor
public class Driver {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String driverIdNumber;
    private String driverLicenseNumber;
    private Date licenseExpDate;
    private Double rating;
    private String username;
    private String password;
    private Boolean isAvailable;
    @Enumerated(value = EnumType.STRING)
    private Role role;
    @Enumerated(value = EnumType.STRING)
    private Status status;

    public Driver(String firstName, String lastName, String phoneNumber,
                  String driverIdNumber, String driverLicenseNumber, Date licenseExpDate,
                  Double rating, String username, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.driverIdNumber = driverIdNumber;
        this.driverLicenseNumber = driverLicenseNumber;
        this.licenseExpDate = licenseExpDate;
        this.rating = rating;
        this.username = username;
        this.password = password;
        this.isAvailable = false;
        this.role = Role.valueOf("DRIVER");
        this.status = Status.valueOf("ACTIVE");
    }
}
