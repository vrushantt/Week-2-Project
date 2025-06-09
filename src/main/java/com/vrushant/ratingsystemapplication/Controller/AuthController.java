package com.vrushant.ratingsystemapplication.Controller;

import com.vrushant.ratingsystemapplication.Model.LoginRequest;
import com.vrushant.ratingsystemapplication.Model.RegisterRequest;
import com.vrushant.ratingsystemapplication.Model.User;
import com.vrushant.ratingsystemapplication.Repository.UserRepository;
import com.vrushant.ratingsystemapplication.Service.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {
    @Autowired
    private UserRepository userRepo;
    @Autowired private JwtUtil jwtUtil;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest req) {
        if (userRepo.findByEmail(req.email).isPresent())
            return ResponseEntity.badRequest().body("Email already registered");
        User user = new User();
        user.setName(req.name);
        user.setEmail(req.email);
        user.setPassword(req.password); // Should hash
        user.setRole("ROLE_USER");
        userRepo.save(user);
        return ResponseEntity.ok("Registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest req) {
        User user = userRepo.findByEmail(req.email).orElse(null);
        if (user == null || !user.getPassword().equals(req.password))
            return ResponseEntity.status(401).body("Invalid credentials");
        return ResponseEntity.ok(jwtUtil.generateToken(user.getEmail(), user.getRole()));
    }
}