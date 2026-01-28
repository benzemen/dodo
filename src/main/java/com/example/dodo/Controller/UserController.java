package com.example.dodo.Controller;


import com.example.dodo.DTO.AuthResponse;
import com.example.dodo.DTO.LoginRequest;
import com.example.dodo.DTO.SignupRequest;
import com.example.dodo.Entity.User;
import com.example.dodo.Security.JwtUtil;
import com.example.dodo.Services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtUtil jwtUtil;


    public ResponseEntity<?> signUp(@RequestBody SignupRequest signupRequest) {

        if (userService.existsByEmail(signupRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body("Email already exists");
        }

        // DTO -> Entity
        User user = new User();
        user.setUseremail(signupRequest.getEmail());
        user.setPassword(
                passwordEncoder.encode(signupRequest.getPassword())
        );

        User savedUser = userService.saveUser(user);
        String token = jwtUtil.generateToken(savedUser.getUseremail());

        Map<String, String> response = new HashMap<>();
        response.put("token", token);
        response.put("message", "User registered successfully");

        return ResponseEntity.ok(response);
    }


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {

        User user = userService.findByEmail(request.getEmail());

        if (user == null || !passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            return ResponseEntity.status(401).body("Invalid credentials");
        }

        String token = jwtUtil.generateToken(user.getUseremail());

        return ResponseEntity.ok(
                new AuthResponse(token, "Login successful")
        );
    }


}
