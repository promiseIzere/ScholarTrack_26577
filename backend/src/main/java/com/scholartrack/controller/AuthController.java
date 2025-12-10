package com.scholartrack.controller;

import com.scholartrack.model.User;
import com.scholartrack.repository.UserRepository;
import com.scholartrack.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService; 

    @Autowired
    private UserDetailsService userDetailsService;

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestParam String username, @RequestParam String password) {
        
        if (username.isBlank() || password.isBlank()) {
            return ResponseEntity.badRequest().build();
        }
        
        Optional<User> userOpt = userRepository.findByUsername(username); 

        if (userOpt.isEmpty()) {
            return new ResponseEntity<>(Map.of("message", "Invalid credentials"), HttpStatus.UNAUTHORIZED);
        }

        User user = userOpt.get();

        if (!passwordEncoder.matches(password, user.getPassword())) {
            return new ResponseEntity<>(Map.of("message", "Invalid credentials"), HttpStatus.UNAUTHORIZED);
        }
        
        // --- JWT GENERATION ---
        // Load the Spring Security UserDetails object to generate claims
        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUsername());
        String jwtToken = jwtService.generateToken(userDetails);
        
        // Return token and role
        return ResponseEntity.ok(Map.of(
            "token", jwtToken,
            "role", user.getRole().name()
        ));
    }
}