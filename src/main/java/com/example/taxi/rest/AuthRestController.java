package com.example.taxi.rest;

import com.example.taxi.constants.Constants;
import com.example.taxi.models.LoginRequest;
import com.example.taxi.entity.Driver;
import com.example.taxi.entity.User;
import com.example.taxi.models.DriverRequest;
import com.example.taxi.models.UserRequest;
import com.example.taxi.repository.DriverRepository;
import com.example.taxi.repository.UserRepository;
import com.example.taxi.security.JwtTokenProvider;
import com.example.taxi.services.DriverService;
import com.example.taxi.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthRestController {

    private final AuthenticationManager authenticationManager;
    private UserRepository userRepository;
    private DriverRepository driverRepository;
    private JwtTokenProvider jwtTokenProvider;
    private UserService userService;
    private DriverService driverService;

    @Autowired
    public AuthRestController(AuthenticationManager authenticationManager,
                              UserRepository userRepository,
                              DriverRepository driverRepository,
                              JwtTokenProvider jwtTokenProvider,
                              UserService userService,
                              DriverService driverService) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.driverRepository = driverRepository;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
        this.driverService = driverService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserRequest userRequest) {
        boolean result = userService.saveUser(userRequest);
        if (!result) {
            return new ResponseEntity<>(Constants.REGISTRATION_FAILED, HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok(userRequest);
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticate(@RequestBody LoginRequest loginRequest) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
            User user = userRepository.findByUsername(loginRequest.getUsername());
            String token = jwtTokenProvider.createToken(loginRequest.getUsername(), user.getRole().name());
            Map<Object, Object> response = new HashMap<>();
            response.put("username", loginRequest.getUsername());
            response.put("token", token);

            return ResponseEntity.ok(response);
        } catch (AuthenticationException e) {
            return new ResponseEntity<>("Invalid username/password combination", HttpStatus.FORBIDDEN);
        }
    }

    @PostMapping("/driver-register")
    public ResponseEntity<?> driverRegister(@RequestBody DriverRequest driverRequest) {
        boolean result = driverService.saveDriver(driverRequest);
        if (!result) {
            return new ResponseEntity<>(Constants.REGISTRATION_FAILED, HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok(driverRequest);
    }

    @PostMapping("/driver-login")
    public ResponseEntity<?> driverAuthenticate(@RequestBody LoginRequest loginRequest) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
            Driver driver = driverRepository.findByUsername(loginRequest.getUsername());
            String token = jwtTokenProvider.createToken(loginRequest.getUsername(), driver.getRole().name());
            Map<Object, Object> response = new HashMap<>();
            response.put("username", loginRequest.getUsername());
            response.put("token", token);

            return ResponseEntity.ok(response);
        } catch (AuthenticationException e) {
            return new ResponseEntity<>("Invalid username/password combination", HttpStatus.FORBIDDEN);
        }
    }

    @PostMapping("/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        SecurityContextLogoutHandler securityContextLogoutHandler = new SecurityContextLogoutHandler();
        securityContextLogoutHandler.logout(request, response, null);
    }
}
