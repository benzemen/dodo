package com.example.dodo.Controller;


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
@RequestMapping("/login")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtUtil jwtUtil;
    @PostMapping("/signup")
    public ResponseEntity<?> SignUp(@RequestBody User user){
        if(userService.existsByEmail(user.getUseremail())){
            return ResponseEntity.badRequest().body("Email already exists");
        }

        // encode karte hai password ko

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User savedUser=userService.saveUser(user);

        // make the jwt token

        String token = jwtUtil.generateToken(savedUser.getUseremail());
        Map<String, String> response=new HashMap<>();
        response.put("token",token);
        response.put("message","User register succesfully");
        return ResponseEntity.ok(response);
    }
    @PostMapping("/login")
    public ResponseEntity<?> Login(@RequestBody User user) {
        User existingUser = userService.findByEmail(user.getUseremail());
        if (existingUser != null && passwordEncoder.matches(user.getPassword(), existingUser.getPassword())) {
            return ResponseEntity.ok("Login successful");
        }
        return ResponseEntity.status(401).body("Invalid credentials");
    }
//    public ResponseEntity<?> Login(@RequestBody LoginRequest loginRequest){
//        String useremail=loginRequest.getEmail();
//        String password=loginRequest.getPassword();
//
//        User user=userService.findByEmail(useremail);
//        if(user==null || !passwordEncoder.matches(password,user.getPassword())){
//            return ResponseEntity.status(401).body("Wrong Password");
//        }
//
//        String token=jwtUtil.generateToken(useremail);
//
//        return ResponseEntity.ok(new AuthResponse(token,"Login successfull"));
//
//    }
}
