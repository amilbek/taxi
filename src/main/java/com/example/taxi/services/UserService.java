package com.example.taxi.services;

import com.example.taxi.entity.User;
import com.example.taxi.helpers.PasswordValidationHelper;
import com.example.taxi.helpers.ValidateHelper;
import com.example.taxi.models.UserRequest;
import com.example.taxi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder encoder;

    public boolean saveUser(UserRequest userRequest) {
        if (!ValidateHelper.validatePhoneNumber(userRequest.getPhoneNumber())) {
            return false;
        }

        User byPhoneNumber = userRepository.findByPhoneNumber(userRequest.getPhoneNumber());
        User byUsername = userRepository.findByUsername(userRequest.getUsername());
        if (byUsername != null || byPhoneNumber != null) {
            return false;
        }

        if (!PasswordValidationHelper.passwordValidation(userRequest.getPassword())) {
            return false;
        }
        User user = new User(userRequest.getFirstName(), userRequest.getLastName(),
                userRequest.getPhoneNumber(), userRequest.getUsername(),
                encoder.encode(userRequest.getPassword()));
        userRepository.save(user);
        return true;
    }
    
    public User getUser(Integer id) {
        return userRepository.findById(id.longValue()).orElse(null);
    }

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public boolean updateUser(UserRequest userRequest) {
        if (!ValidateHelper.validatePhoneNumber(userRequest.getPhoneNumber())) {
            return false;
        }
        if (!PasswordValidationHelper.passwordValidation(userRequest.getPassword())) {
            return false;
        }
        Optional<User> userOptional = userRepository.findById(userRequest.getId().longValue());
        User user = userOptional.orElse(null);
        assert user != null;
        user.setFirstName(userRequest.getFirstName());
        user.setLastName(userRequest.getLastName());
        user.setUsername(userRequest.getUsername());
        user.setPassword(userRequest.getPassword());
        user.setPhoneNumber(userRequest.getPhoneNumber());
        userRepository.save(user);
        return true;
    }

    public boolean deleteUser(Integer id) {
        Optional<User> userOptional = userRepository.findById(id.longValue());
        User user = userOptional.orElse(null);
        assert user != null;
        userRepository.delete(user);
        return true;
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        userRepository.findAll().forEach(users::add);
        return users;
    }
}