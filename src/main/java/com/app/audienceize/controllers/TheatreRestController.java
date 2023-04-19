package com.app.audienceize.controllers;

import com.app.audienceize.dtos.requests.TheatreRequest;
import com.app.audienceize.services.TheatreService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/theatres")
public class TheatreRestController {
    @Autowired
    private TheatreService theatreService;

    @PostMapping
    public ResponseEntity<String> addTheatre(@Valid @RequestBody TheatreRequest request) {
        String res = theatreService.addTheatre(request);
        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }
}
