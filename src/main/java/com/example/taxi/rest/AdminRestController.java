package com.example.taxi.rest;

import com.example.taxi.constants.Constants;
import com.example.taxi.models.CarTariffRequest;
import com.example.taxi.models.DriverStatusRequest;
import com.example.taxi.services.CarService;
import com.example.taxi.services.DriverService;
import com.example.taxi.services.OrderService;
import com.example.taxi.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/*
ToDo:
    Make Driver Available +
    Define Car Tariff +
    Delete User +
    Delete Driver +
    Get all Orders +
    Get all Users +
    Get all Drivers +
    Get all Cars +
*/

@RestController
@RequestMapping("/admin")
public class AdminRestController {

    private UserService userService;
    private DriverService driverService;
    private CarService carService;
    private OrderService orderService;

    @Autowired
    public AdminRestController(UserService userService, DriverService driverService, CarService carService, OrderService orderService) {
        this.userService = userService;
        this.driverService = driverService;
        this.carService = carService;
        this.orderService = orderService;
    }

    @PostMapping("/driver-status")
    public ResponseEntity<?> changeDriverStatus(@RequestBody DriverStatusRequest driverStatusRequest) {
        boolean result = driverService.changeStatus(driverStatusRequest);
        if (!result) {
            return new ResponseEntity<>(Constants.SOMETHING_WRONG, HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok().body("");
    }

    @PostMapping("/car-tariff")
    public ResponseEntity<?> changeTariff(@RequestBody CarTariffRequest carTariffRequest) {
        boolean result = carService.changeTariff(carTariffRequest);
        if (!result) {
            return new ResponseEntity<>(Constants.SOMETHING_WRONG, HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok().body("");
    }

    @GetMapping("/delete-user")
    public ResponseEntity<?> deleteUser(@RequestParam Integer id) {
        boolean result = userService.deleteUser(id);
        if (!result) {
            return new ResponseEntity<>(Constants.SOMETHING_WRONG, HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok().body("");
    }

    @GetMapping("/delete-driver")
    public ResponseEntity<?> deleteDriver(@RequestParam Integer id) {
        boolean result = driverService.deleteDriver(id);
        if (!result) {
            return new ResponseEntity<>(Constants.SOMETHING_WRONG, HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok().body("");
    }

    @GetMapping("/all-users")
    public ResponseEntity<?> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/all-drivers")
    public ResponseEntity<?> getAllDrivers() {
        return ResponseEntity.ok(driverService.getAllDrivers());
    }

    @GetMapping("/all-cars")
    public ResponseEntity<?> getAllCars() {
        return ResponseEntity.ok(carService.getAllCars());
    }

    @GetMapping("/all-orders")
    public ResponseEntity<?> getAllOrders() {
        return ResponseEntity.ok(orderService.getAllOrders());
    }
}
