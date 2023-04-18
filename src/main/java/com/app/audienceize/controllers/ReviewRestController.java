package com.app.audienceize.controllers;

import com.app.audienceize.dtos.requests.ReviewRequest;
import com.app.audienceize.services.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/reviews")
public class ReviewRestController {
    @Autowired
    private ReviewService reviewService;

    @PostMapping
    public ResponseEntity<String> addReview(@RequestBody ReviewRequest request) {
        String res = reviewService.addReview(request);
        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }
}
