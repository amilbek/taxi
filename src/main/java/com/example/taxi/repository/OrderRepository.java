package com.example.taxi.repository;

import com.example.taxi.entity.Driver;
import com.example.taxi.entity.Order;
import com.example.taxi.entity.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface OrderRepository extends CrudRepository<Order, Long> {
    Order findById(Integer id);
    Order findAllByDriver(Driver driver);
    Order findAllByUser(User user);
}
