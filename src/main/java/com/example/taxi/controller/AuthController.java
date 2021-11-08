package com.example.taxi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthController {

    @GetMapping("/login")
    public String login() {
        return "user-login";
    }

    @GetMapping("/register")
    public String register() {
        return "user-register";
    }

    @GetMapping("/driver-login")
    public String driverLogin() {
        return "driver-login";
    }

    @GetMapping("/driver-register")
    public String driverRegister() {
        return "driver-register";
    }
}
