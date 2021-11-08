package com.example.taxi.services;

import com.example.taxi.entity.Driver;
import com.example.taxi.helpers.PasswordValidationHelper;
import com.example.taxi.helpers.ValidateHelper;
import com.example.taxi.models.DriverRequest;
import com.example.taxi.models.DriverStatusRequest;
import com.example.taxi.repository.DriverRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DriverService {
    @Autowired
    private DriverRepository driverRepository;

    public boolean saveDriver(DriverRequest driverRequest) {
        if (!ValidateHelper.validatePhoneNumber(driverRequest.getPhoneNumber())) {
            return false;
        }
        Driver byPhoneNumber =
                driverRepository.findByPhoneNumber(driverRequest.getPhoneNumber());
        Driver byUsername = driverRepository.findByUsername(driverRequest.getUsername());
        if (byUsername != null || byPhoneNumber != null) {
            return false;
        }

        if (!PasswordValidationHelper.passwordValidation(driverRequest.getPassword())) {
            return false;
        }
        Driver driver = new Driver(driverRequest.getFirstName(),
                driverRequest.getLastName(), driverRequest.getPhoneNumber(),
                driverRequest.getDriverIdNumber(),
                driverRequest.getDriverLicenseNumber(), driverRequest.getLicenseExpDate(),
                driverRequest.getRating(), driverRequest.getUsername(),
                driverRequest.getPassword());
        driverRepository.save(driver);
        return true;
    }

    public Driver getDriver(Integer id) {
        return driverRepository.findById(id.longValue()).orElse(null);
    }

    public Driver getDriverByUsername(String username) {
        return driverRepository.findByUsername(username);
    }

    public boolean updateDriver(DriverRequest driverRequest) {
        if (!ValidateHelper.validatePhoneNumber(driverRequest.getPhoneNumber())) {
            return false;
        }
        if (!PasswordValidationHelper.passwordValidation(driverRequest.getPassword())) {
            return false;
        }
        Optional<Driver> driverOptional =
                driverRepository.findById(driverRequest.getId().longValue());
        Driver driver = driverOptional.orElse(null);
        if (driver == null) {
            return false;
        }
        driver.setFirstName(driverRequest.getFirstName());
        driver.setLastName(driverRequest.getLastName());
        driver.setPhoneNumber(driverRequest.getPhoneNumber());
        driver.setDriverIdNumber(driverRequest.getDriverIdNumber());
        driver.setDriverLicenseNumber(driverRequest.getDriverLicenseNumber());
        driver.setLicenseExpDate(driverRequest.getLicenseExpDate());
        driver.setUsername(driverRequest.getUsername());
        driver.setPassword(driverRequest.getPassword());
        driverRepository.save(driver);
        return true;
    }

    public boolean deleteDriver(Integer id) {
        Optional<Driver> driverOptional = driverRepository.findById(id.longValue());
        Driver driver = driverOptional.orElse(null);
        driverRepository.delete(driver);
        return true;
    }

    public boolean changeStatus(DriverStatusRequest driverStatusRequest) {
        Optional<Driver> driverOptional =
                driverRepository.findById(driverStatusRequest.getDriverId().longValue());
        Driver driver = driverOptional.orElse(null);
        if (driver == null) {
            return false;
        }
        driver.setIsAvailable(driverStatusRequest.getDriverStatus());
        driverRepository.save(driver);
        return true;
    }

    public List<Driver> getAllDrivers() {
        List<Driver> drivers = new ArrayList<>();
        driverRepository.findAll().forEach(drivers::add);
        return drivers;
    }
}
