package com.example.taxi.repository;

import com.example.taxi.entity.Driver;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface DriverRepository extends CrudRepository<Driver, Long> {
    Driver findById(Integer id);
    Driver findByUsername(String username);
    Driver findByPhoneNumber(String phoneNumber);
}
