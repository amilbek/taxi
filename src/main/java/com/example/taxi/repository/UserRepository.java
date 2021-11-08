package com.example.taxi.repository;

import com.example.taxi.entity.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRepository extends CrudRepository<User, Long> {
    User findById(Integer id);
    User findByUsername(String username);
    User findByPhoneNumber(String phoneNumber);
}
