package com.scholartrack.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestParam String username, @RequestParam String password) {
        // Placeholder: in future, validate via Security + JWT
        if (username.isBlank() || password.isBlank()) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(Map.of("token", "dev-token"));
    }
}


