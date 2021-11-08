package com.example.taxi.rest;

import com.example.taxi.constants.Constants;
import com.example.taxi.entity.Car;
import com.example.taxi.entity.Driver;
import com.example.taxi.models.DriverOrderRequest;
import com.example.taxi.models.DriverRequest;
import com.example.taxi.services.CarService;
import com.example.taxi.services.DriverService;
import com.example.taxi.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/*
ToDo:
    Register Driver +
    Get Driver +
    Edit Driver +
    Delete Driver +
    Accept Order +
    Get Driver Orders +
*/

@RestController
@RequestMapping("/drivers")
public class DriverRestController {

    private DriverService driverService;
    private CarService carService;
    private OrderService orderService;

    @Autowired
    public DriverRestController(DriverService driverService, CarService carService, OrderService orderService) {
        this.driverService = driverService;
        this.carService = carService;
        this.orderService = orderService;
    }

    @GetMapping("/driver")
    public ResponseEntity<?> getDriverById(@RequestParam Integer id) {
        Driver driver = driverService.getDriver(id);
        if (driver == null) {
            return new ResponseEntity<>(Constants.DRIVER_NOT_FOUND, HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(driver);
    }

    @GetMapping("/profile")
    public ResponseEntity<?> getDriverByUsername(@RequestParam String username) {
        Driver driver = driverService.getDriverByUsername(username);
        if (driver == null) {
            return new ResponseEntity<>(Constants.DRIVER_NOT_FOUND, HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(driver);
    }

    @PostMapping("/edit")
    public ResponseEntity<?> updateDriver(@RequestBody DriverRequest driverRequest) {
        boolean result = driverService.updateDriver(driverRequest);
        if (!result) {
            return new ResponseEntity<>(Constants.EDITING_FAILED, HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(driverRequest);
    }

    @GetMapping("/delete")
    public ResponseEntity<?> deleteDriver(@RequestParam Integer id) {
        boolean result1 = driverService.deleteDriver(id);
        if (!result1) {
            return new ResponseEntity<>(Constants.DELETING_FAILED, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(Constants.DELETED_SUCCESSFULLY, HttpStatus.OK);
    }

    @PostMapping("/accept-order")
    public ResponseEntity<?> acceptOrder(@RequestBody DriverOrderRequest driverOrderRequest) {
        boolean result = orderService.addDriverToOrder(driverOrderRequest.getDriverId(),
                driverOrderRequest.getOrderId());
        if (!result) {
            return new ResponseEntity<>(Constants.SOMETHING_WRONG, HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok().body("");
    }

    @GetMapping("/car")
    public ResponseEntity<?> getCar(@RequestParam Integer id) {
        Car car = carService.getCarByDriver(id);
        if (car == null) {
            return new ResponseEntity<>(Constants.SOMETHING_WRONG, HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok(car);
    }

    @GetMapping("/history")
    public ResponseEntity<?> getMyOrders(@RequestParam Integer id) {
        return ResponseEntity.ok(orderService.getOrdersByDriver(id));
    }

    @GetMapping("/orders")
    public ResponseEntity<?> getOrders() {
        return ResponseEntity.ok(orderService.getAvailableOrders());
    }
}
