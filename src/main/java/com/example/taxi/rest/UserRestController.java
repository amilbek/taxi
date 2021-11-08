package com.example.taxi.rest;

import com.example.taxi.constants.Constants;
import com.example.taxi.entity.User;
import com.example.taxi.models.UserOrderRequest;
import com.example.taxi.models.UserRequest;
import com.example.taxi.services.OrderService;
import com.example.taxi.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/*
ToDo:
    Register User +
    Get User +
    Edit User +
    Delete User +
    Rating to Driver -
    Get User Orders +
*/

@RestController
@RequestMapping("/users")
public class UserRestController {

    @Autowired
    private UserService userService;

    @Autowired
    private OrderService orderService;

    @GetMapping("/user")
    public ResponseEntity<?> getUserById(@RequestParam Integer id) {
        User user = userService.getUser(id);
        if (user == null) {
            return new ResponseEntity<>(Constants.USER_NOT_FOUND, HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(user);
    }

    @GetMapping("/profile")
    public ResponseEntity<?> getUserByUsername(@RequestParam String username) {
        User user = userService.getUserByUsername(username);
        if (user == null) {
            return new ResponseEntity<>(Constants.USER_NOT_FOUND, HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(user);
    }

    @PostMapping("/edit")
    public ResponseEntity<?> updateUser(@RequestBody UserRequest userRequest) {
        boolean result = userService.updateUser(userRequest);
        if (!result) {
            return new ResponseEntity<>(Constants.EDITING_FAILED, HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(userRequest);
    }

    @GetMapping("/delete")
    public ResponseEntity<?> deleteUser(@RequestParam Integer id) {
        boolean result = userService.deleteUser(id);
        if (!result) {
            return new ResponseEntity<>(Constants.DELETING_FAILED, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(Constants.DELETED_SUCCESSFULLY, HttpStatus.OK);
    }

    @PostMapping("/add-order")
    public ResponseEntity<?> addOrder(@RequestBody UserOrderRequest userOrderRequest) {
        orderService.addOrderToUser(userOrderRequest.getUserId(),
                userOrderRequest.getOrderId());
        return ResponseEntity.ok().body("");
    }

    @GetMapping("/history")
    public ResponseEntity<?> getMyOrders(@RequestParam Integer id) {
        return ResponseEntity.ok(orderService.getOrdersByUser(id));
    }
}
