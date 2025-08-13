package com.example.greencart.controller;

import com.example.greencart.model.User;
import com.example.greencart.repository.UserRepository;
import com.example.greencart.util.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@CrossOrigin
public class AuthController {
    private final UserRepository userRepo;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthController(UserRepository userRepo, JwtUtil jwtUtil, BCryptPasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/register")
    public String register(@RequestBody User user) {
        user.setPasswordHash(passwordEncoder.encode(user.getPasswordHash()));
        userRepo.save(user);
        return "User registered!";
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> body) {
        String username = body.get("username");
        String password = body.get("password");
        return userRepo.findByUsername(username).map(user -> {
            if (passwordEncoder.matches(password, user.getPasswordHash())) {
                String token = jwtUtil.generateToken(user.getUsername());
                return ResponseEntity.ok(Map.of("token", token));
            } else {
                return ResponseEntity.status(401).body(Map.of("message", "invalid credentials"));
            }
        }).orElseGet(() -> ResponseEntity.status(401).body(Map.of("message", "invalid credentials")));
    }
}
