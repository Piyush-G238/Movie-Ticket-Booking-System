package com.app.audienceize.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
public class HomeRestController {
    @GetMapping("/")
    public ResponseEntity<Map<String, String>> home(){
        Map<String, String> map = new HashMap<>();
        map.put("project-title", "Movie-ticket-booking-application");
        map.put("created-by", "Piyush Gupta");
        map.put("description", "REST Application for booking movie tickets and reviewing movies");
        map.put("timestamp", LocalDateTime.now().toString());
        return ResponseEntity.ok(map);
    }
}
