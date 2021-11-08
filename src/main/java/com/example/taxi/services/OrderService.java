package com.example.taxi.services;

import com.example.taxi.entity.Driver;
import com.example.taxi.entity.Order;
import com.example.taxi.entity.User;
import com.example.taxi.models.OrderRequest;
import com.example.taxi.repository.DriverRepository;
import com.example.taxi.repository.OrderRepository;
import com.example.taxi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import static java.lang.Math.max;
import static java.lang.Math.sqrt;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DriverRepository driverRepository;

    public boolean saveOrder(OrderRequest orderRequest) {
        Order order = new Order(orderRequest.getAddressFrom(), orderRequest.getAddressTo(),
                getDistance(), orderRequest.getTariff(), getPrice(orderRequest.getTariff()));
        orderRepository.save(order);
        return true;
    }

    public Order getOrder(Integer id) {
        return orderRepository.findById(id.longValue()).orElse(null);
    }

    public boolean startOrder(Integer id) {
        Optional<Order> orderOptional = orderRepository.findById(id.longValue());
        Order order = orderOptional.orElse(null);
        if (!order.getOrderStatus().equals("ordered")) {
            return false;
        }
        order.setOrderStatus("started");
        order.setOrderEndTime(dateTimeFormatter());
        orderRepository.save(order);
        return true;
    }

    public boolean cancelOrder(Integer id) {
        Optional<Order> orderOptional = orderRepository.findById(id.longValue());
        Order order = orderOptional.orElse(null);
        if (order.getOrderStatus().equals("completed")) {
            return false;
        }
        order.setOrderStatus("canceled");
        order.setOrderEndTime(dateTimeFormatter());

        orderRepository.save(order);
        return true;
    }

    public boolean completeOrder(Integer id) {
        Optional<Order> orderOptional = orderRepository.findById(id.longValue());
        Order order = orderOptional.orElse(null);
        if (!order.getOrderStatus().equals("started")) {
            return false;
        }
        order.setOrderStatus("completed");
        order.setOrderEndTime(dateTimeFormatter());
        orderRepository.save(order);
        return true;
    }

    public double getDistance() {
        double min = 0.0;
        double max = 10.0;
        double addressFromX = (Math.random() * ((max - min) + 1)) + min;
        double addressToX = (Math.random() * ((max - min) + 1)) + min;
        double addressFromY = (Math.random() * ((max - min) + 1)) + min;
        double addressToY = (Math.random() * ((max - min) + 1)) + min;
        double x = addressToX - addressFromX;
        double y = addressToY - addressFromY;
        return Math.ceil(sqrt(Math.pow(x, 2) + Math.pow(y, 2)));
    }

    public double getPrice(String chosenTariff) {
        double tariffPrice = 0.0;
        switch (chosenTariff) {
            case "Economy":
                tariffPrice = 80.0;
                break;
            case "Comfort":
                tariffPrice = 120.0;
                break;
            case "Business":
                tariffPrice = 140.0;
                break;
            case "Minivan":
                tariffPrice = 150.0;
                break;
        }
        return getDistance() * tariffPrice;
    }

    public boolean addOrderToUser(Integer userId, Integer orderId) {
        Optional<Order> orderOptional = orderRepository.findById(orderId.longValue());
        Order order = orderOptional.orElse(null);
        if (order == null) {
            return false;
        }
        Optional<User> userOptional = userRepository.findById(userId.longValue());
        User user = userOptional.orElse(null);
        order.setUser(user);
        orderRepository.save(order);
        return true;
    }

    public boolean addDriverToOrder(Integer driverId, Integer orderId) {
        Optional<Order> orderOptional = orderRepository.findById(orderId.longValue());
        Order order = orderOptional.orElse(null);
        if (order == null) {
            return false;
        }
        Optional<Driver> driverOptional =
                driverRepository.findById(driverId.longValue());
        Driver driver = driverOptional.orElse(null);
        if (!driver.getIsAvailable()) {
            return false;
        }
        order.setDriver(driver);
        orderRepository.save(order);
        return true;
    }

    private String dateTimeFormatter() {
        LocalDateTime currentTime = LocalDateTime.now();
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        return currentTime.format(myFormatObj);
    }

    public List<Order> getAllOrders() {
        List<Order> orders = new ArrayList<>();
        orderRepository.findAll().forEach(orders::add);
        return orders;
    }

    public List<Order> getOrdersByDriver(Integer id) {
        Optional<Driver> driverOptional = driverRepository.findById(id.longValue());
        Driver driver = driverOptional.orElse(null);
        List<Order> orders = new ArrayList<>();
        orders.add(orderRepository.findAllByDriver(driver));
        return orders;
    }

    public List<Order> getOrdersByUser(Integer id) {
        Optional<User> userOptional = userRepository.findById(id.longValue());
        User user = userOptional.orElse(null);
        List<Order> orders = new ArrayList<>();
        orders.add(orderRepository.findAllByUser(user));
        return orders;
    }

    public List<Order> getAvailableOrders() {
        List<Order> orders = new ArrayList<>();
        orderRepository.findAll().forEach(order -> {
            if (order.getOrderStatus().equals("started")) {
                orders.add(order);
            }
        });
        return orders;
    }
}
