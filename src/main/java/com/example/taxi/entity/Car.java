package com.example.taxi.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="cars")
@Getter
@Setter
@NoArgsConstructor
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String carNumber;
    private String carModel;
    private String carColor;
    private String tariff;

    @OneToOne(targetEntity=Driver.class,cascade=CascadeType.ALL)
    private Driver driver;

    public Car(String carNumber, String carModel, String carColor, String tariff) {
        this.carNumber = carNumber;
        this.carModel = carModel;
        this.carColor = carColor;
        this.tariff = tariff;
    }
}
