package com.app.audienceize.controllers;

import com.app.audienceize.dtos.requests.UserRequest;
import com.app.audienceize.services.interfaces.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class UserRestController {
    @Autowired
    private UserService service;

    @PostMapping("/signup")
    public ResponseEntity<String> addUser(@Valid @RequestBody UserRequest request) {
        String response = service.addUser(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Map<String, String> credentials) {
        String token = service.login(credentials);
        return new ResponseEntity<>(token, HttpStatus.CREATED);
    }
}
