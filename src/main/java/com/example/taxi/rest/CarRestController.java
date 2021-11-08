package com.example.taxi.rest;

import com.example.taxi.constants.Constants;
import com.example.taxi.entity.Car;
import com.example.taxi.models.CarRequest;
import com.example.taxi.models.DriverCarRequest;
import com.example.taxi.services.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/*
ToDo:
    Add Car +
    Get Car +
    Edit Car +
    Delete Car +
    Add Driver +
*/

@RestController
@RequestMapping("/cars")
public class CarRestController {

    @Autowired
    private CarService carService;

    @PostMapping("/add-car")
    public ResponseEntity createCar(@RequestBody CarRequest carRequest) {
        boolean result = carService.saveCar(carRequest);
        if (!result) {
            return new ResponseEntity(Constants.ADDING_CAR_FAILED, HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok(carRequest);
    }

    @GetMapping("/car")
    public ResponseEntity getCarById(@RequestParam Integer id) {
        Car car = carService.getCarById(id);
        if (car == null) {
            return new ResponseEntity(Constants.CAR_NOT_FOUND, HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(car);
    }

    @GetMapping("/car-number")
    public ResponseEntity getCarByCarNumber(@RequestParam String carNumber) {
        Car car = carService.getCarByCarNumber(carNumber);
        if (car == null) {
            return new ResponseEntity(Constants.CAR_NOT_FOUND, HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(car);
    }

    @PostMapping("/edit")
    public ResponseEntity updateCar(@RequestBody CarRequest carRequest) {
        boolean result = carService.updateCar(carRequest);
        if (!result) {
            return new ResponseEntity(Constants.EDITING_FAILED, HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(carRequest);
    }

    @GetMapping("/delete")
    public ResponseEntity deleteCarById(@RequestParam Integer id) {
        boolean result = carService.deleteCarById(id);
        if (!result) {
            return new ResponseEntity(Constants.DELETING_FAILED, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(Constants.DELETED_SUCCESSFULLY, HttpStatus.OK);
    }

    @PostMapping("/add-driver")
    public ResponseEntity addCar(@RequestBody DriverCarRequest driverCarRequest) {
        carService.addCarToDriver(driverCarRequest.getDriverId(),
                driverCarRequest.getCarId());
        return ResponseEntity.ok().body("");
    }
}
