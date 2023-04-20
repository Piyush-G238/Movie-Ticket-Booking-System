package com.app.audienceize.controllers;

import com.app.audienceize.dtos.requests.ShowRequest;
import com.app.audienceize.services.ShowService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/shows")
public class ShowRestController {

    @Autowired
    private ShowService showService;

    @PostMapping
    public ResponseEntity<String> addShow(@Valid @RequestBody ShowRequest request) {
        String res = showService.addShow(request);
        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }
}
