package com.example.taxi.rest;

import com.example.taxi.constants.Constants;
import com.example.taxi.entity.Order;
import com.example.taxi.models.OrderRequest;
import com.example.taxi.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/*
ToDo:
    Taxi Order +
    Get Order +
    Cancel Order +
    Complete Order +
    Add User +
    Add Driver +
    Round Distance and Price +
*/

@RestController
@RequestMapping("/orders")
public class OrderRestController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/users/taxi-order")
    public ResponseEntity createOrder(@RequestBody OrderRequest orderRequest) {
        boolean result = orderService.saveOrder(orderRequest);
        if (!result) {
            return new ResponseEntity(Constants.ORDERED_SUCCESSFULLY, HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(orderRequest);
    }

    @GetMapping("/users/order")
    public ResponseEntity getOrder(@RequestParam Integer id) {
        Order order = orderService.getOrder(id);
        if (order == null) {
            return new ResponseEntity(Constants.ORDER_NOT_FOUND, HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(order);
    }

    @GetMapping("/users/cancel-order")
    public ResponseEntity cancelOrder(@RequestParam Integer id) {
        boolean result = orderService.cancelOrder(id);
        if (!result) {
            return new ResponseEntity(Constants.SOMETHING_WRONG, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(Constants.CANCELED_SUCCESSFULLY, HttpStatus.OK);
    }

    @GetMapping("/drivers/complete-order")
    public ResponseEntity completeOrder(@RequestParam Integer id) {
        boolean result = orderService.completeOrder(id);
        if (!result) {
            return new ResponseEntity(Constants.SOMETHING_WRONG, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(Constants.COMPLETED_SUCCESSFULLY, HttpStatus.OK);
    }

    @GetMapping("/drivers/start-order")
    public ResponseEntity startOrder(@RequestParam Integer id) {
        boolean result = orderService.startOrder(id);
        if (!result) {
            return new ResponseEntity(Constants.SOMETHING_WRONG, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(Constants.START_ORDER, HttpStatus.OK);
    }
}