package com.app.audienceize.controllers;

import com.app.audienceize.dtos.requests.MovieRequest;
import com.app.audienceize.services.MovieService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/movies")
public class MovieRestController {
    @Autowired
    private MovieService movieService;
    @GetMapping("/test")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("GET request on /test endpoint works");
    }
    @PostMapping
    public ResponseEntity<String> addMovie(@Valid @RequestBody MovieRequest request) {
        String body = movieService.addMovie(request);
        if (body.contains("already exists.")){
            return new ResponseEntity<>(body, HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(body, HttpStatus.CREATED);
    }
}
