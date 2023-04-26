package com.app.audienceize.controllers;

import com.app.audienceize.dtos.requests.ReviewRequest;
import com.app.audienceize.dtos.responses.ReviewResponse;
import com.app.audienceize.services.impl.JwtAuthService;
import com.app.audienceize.services.interfaces.ReviewService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/reviews")
public class ReviewRestController {
    @Autowired
    private ReviewService reviewService;

    @Autowired
    private JwtAuthService jwtAuthService;
    @PostMapping
    public ResponseEntity<String> addReview(@Valid @RequestBody ReviewRequest request, @RequestHeader(HttpHeaders.AUTHORIZATION) String bearer) {
        String jwtToken = bearer.substring(7);
        String username = jwtAuthService.extractUsername(jwtToken);
        String res = reviewService.addReview(request, username);
        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }
    @GetMapping("/title")
    public ResponseEntity<List<ReviewResponse>> getTop5ReviewsForMovie(@RequestParam(name = "name") String name) {
        List<ReviewResponse> responses = reviewService.getTop5ReviewsByMovieTitle(name);
        return ResponseEntity.ok(responses);
    }

    @PutMapping("/{reviewId}")
    public ResponseEntity<String> updateReview(@PathVariable String reviewId, @Valid @RequestBody ReviewRequest request) {
        return ResponseEntity.ok(reviewService.updateReview(request, reviewId));
    }

    @DeleteMapping("/{reviewId}")
    public ResponseEntity<String> deleteReview(@PathVariable String reviewId) {
        return ResponseEntity.ok(reviewService.deleteReview(reviewId));
    }
    @GetMapping
    public ResponseEntity<List<ReviewResponse>> listAllReviewsOfLoggedUser(@RequestHeader(HttpHeaders.AUTHORIZATION) String bearer) {
        String username = jwtAuthService.extractUsername(bearer.substring(7));
        return ResponseEntity.ok(reviewService.getAllReviewsOfLoggedUser(username));
    }
}
