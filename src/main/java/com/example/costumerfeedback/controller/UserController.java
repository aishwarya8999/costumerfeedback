package com.example.costumerfeedback.controller;

import com.example.costumerfeedback.dto.LoginRequest;
import com.example.costumerfeedback.dto.RegisterRequest;
import com.example.costumerfeedback.model.Users;
import com.example.costumerfeedback.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController
{
    private final UserService service;

    @PostMapping("/register")
    public ResponseEntity<Users> registerUsers(@RequestBody RegisterRequest request)
    {
        return ResponseEntity.ok(service.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody LoginRequest request)
    {

        String token = service.login(request);
        return ResponseEntity.ok(Map.of("token", token));
    }
}
