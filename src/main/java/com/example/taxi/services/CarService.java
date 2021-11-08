package com.example.taxi.services;

import com.example.taxi.entity.Car;
import com.example.taxi.entity.Driver;
import com.example.taxi.models.CarRequest;
import com.example.taxi.repository.CarRepository;
import com.example.taxi.models.CarTariffRequest;
import com.example.taxi.repository.DriverRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CarService {

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private DriverRepository driverRepository;

    public boolean saveCar(CarRequest carRequest) {
        Car byCarNumber = carRepository.findByCarNumber(carRequest.getCarNumber());
        if (byCarNumber != null ) {
            return false;
        }
        Car car = new Car(carRequest.getCarNumber(), carRequest.getCarModel(),
                carRequest.getCarColor(),
                carRequest.getTariff());
        carRepository.save(car);
        return true;
    }

    public Car getCarById(Integer id) {
        return carRepository.findById(id.longValue()).orElse(null);
    }

    public Car getCarByCarNumber(String carNumber) {
        return carRepository.findByCarNumber(carNumber);
    }

    public boolean updateCar(CarRequest carRequest) {
        Optional<Car> carOptional = carRepository.findById(carRequest.getId().longValue());
        Car car = carOptional.orElse(null);
        car.setCarNumber(carRequest.getCarNumber());
        car.setCarModel(carRequest.getCarModel());
        carRepository.save(car);
        return true;
    }

    public boolean deleteCarById(Integer id) {
        Optional<Car> carOptional = carRepository.findById(id.longValue());
        Car car = carOptional.orElse(null);
        carRepository.delete(car);
        return true;
    }

    public boolean addCarToDriver(Integer driverId, Integer carId) {
        Optional<Car> carOptional = carRepository.findById(carId.longValue());
        Car car = carOptional.orElse(null);
        if (car == null) {
            return false;
        }
        Optional<Driver> driverOptional = driverRepository.findById(driverId.longValue());
        Driver driver = driverOptional.orElse(null);
        car.setDriver(driver);
        carRepository.save(car);
        return true;
    }

    public boolean changeTariff(CarTariffRequest carTariffRequest) {
        Optional<Car> carOptional = carRepository.findById(carTariffRequest.getId().longValue());
        Car car = carOptional.orElse(null);
        if (car == null) {
            return false;
        }
        car.setTariff(carTariffRequest.getTariff());
        carRepository.save(car);
        return true;
    }

    public List<Car> getAllCars() {
        List<Car> cars = new ArrayList<>();
        carRepository.findAll().forEach(cars::add);
        return cars;
    }

    public Car getCarByDriver(Integer id) {
        return carRepository.findCarByDriver(id);
    }
}
