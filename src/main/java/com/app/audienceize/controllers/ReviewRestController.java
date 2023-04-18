package com.app.audienceize.controllers;

import com.app.audienceize.dtos.requests.ReviewRequest;
import com.app.audienceize.dtos.responses.ReviewResponse;
import com.app.audienceize.services.ReviewService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/reviews")
public class ReviewRestController {
    @Autowired
    private ReviewService reviewService;

    @PostMapping
    public ResponseEntity<String> addReview(@Valid @RequestBody ReviewRequest request) {
        String res = reviewService.addReview(request);
        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }
    @GetMapping
    public ResponseEntity<List<?>> getTop5ReviewsForMovie(@RequestParam(name = "title") String title) {
        List<ReviewResponse> responses = reviewService.getTop5ReviewsByMovieTitle(title);
        return ResponseEntity.ok(responses);
    }
}
