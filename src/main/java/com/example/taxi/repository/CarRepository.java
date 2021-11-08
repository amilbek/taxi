package com.example.taxi.repository;

import com.example.taxi.entity.Car;
import org.springframework.data.repository.CrudRepository;

public interface CarRepository extends CrudRepository<Car, Long> {
    Car findById(Integer id);
    Car findByCarNumber(String carNumber);
    Car findCarByDriver(Integer id);
    void deleteCarByDriver(Integer driverId);
}
