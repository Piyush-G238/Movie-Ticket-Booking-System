package com.app.audienceize.controllers;

import com.app.audienceize.dtos.requests.ReviewRequest;
import com.app.audienceize.dtos.responses.ReviewResponse;
import com.app.audienceize.services.impl.JwtAuthService;
import com.app.audienceize.services.interfaces.ReviewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/reviews")
@Tag(name = "Review Rest Apis")
public class ReviewRestController {
    @Autowired
    private ReviewService reviewService;

    @Autowired
    private JwtAuthService jwtAuthService;

    @Operation(
            method = "POST",
            description = "Endpoint for creating a review for particular movie",
            summary = "Endpoint for creating a review for particular movie",
            parameters = {@Parameter(name = "", description = "No parameters required", required = false)},
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "CREATED",
                            content = {
                                    @Content(
                                            mediaType = "TEXT_PLAIN",
                                            examples = {
                                                    @ExampleObject(
                                                            value = "Your review has been added for the movie The Avengers Endgame"
                                                    )
                                            }
                                    )
                            }
                    )
            }
    )
    @PostMapping
    public ResponseEntity<String> addReview(@Valid @RequestBody ReviewRequest request, @RequestHeader(HttpHeaders.AUTHORIZATION) String bearer) {
        String jwtToken = bearer.substring(7);
        String username = jwtAuthService.extractUsername(jwtToken);
        String res = reviewService.addReview(request, username);
        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }

    @Operation(
            method = "GET",
            description = "Endpoint for fetching top five reviews for a particular movie sorted by its rating.",
            summary = "Endpoint for fetching top five reviews for a particular movie",
            parameters = {
                    @Parameter(
                            name = "name",
                            required = true,
                            example = "The Avengers Endgame"
                    )
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK",
                            content = {
                                    @Content(
                                            mediaType = "APPLICATION_JSON",
                                            examples = {
                                                    @ExampleObject(
                                                            value = """
                                                                    [
                                                                      {
                                                                        "id":"1d661ce1-d372-4406-bafc-8130a2192ac5",
                                                                        "movieTitle":"The Avengers endgame",
                                                                        "rating":4.0,
                                                                        "comment":"One of the best movie by Marvel cinematic universe",
                                                                        "postedOn":"2023-05-14T15:02:00"
                                                                      }
                                                                    ]
                                                                    """
                                                    )
                                            }
                                    )
                            }
                    )
            }
    )
    @GetMapping("/title")
    public ResponseEntity<List<ReviewResponse>> getTop5ReviewsForMovie(@RequestParam(name = "name") String name) {
        List<ReviewResponse> responses = reviewService.getTop5ReviewsByMovieTitle(name);
        return ResponseEntity.ok(responses);
    }

    @Operation(
            method = "PUT",
            description = "Endpoint to update the review posted by the authenticated user.",
            summary = "Endpoint to update the review posted by the authenticated user.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK",
                            content = {
                                    @Content(
                                            mediaType = "TEXT_PLAIN",
                                            examples = {@ExampleObject(value = "Hey, your review has been updated")}
                                    )
                            }
                    )
            }

    )
    @PutMapping("/{reviewId}")
    public ResponseEntity<String> updateReview(@PathVariable String reviewId, @Valid @RequestBody ReviewRequest request) {
        return ResponseEntity.ok(reviewService.updateReview(request, reviewId));
    }

    @Operation(
            method = "DELETE",
            description = "Endpoint to delete a review of authenticated user",
            summary = "Endpoint to delete a review of authenticated user",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK",
                            content = {
                                    @Content(
                                            mediaType = "APPLICATION_JSON",
                                            examples = {
                                                    @ExampleObject(
                                                            value = "Hey, your review has been removed."
                                                    )
                                            }
                                    )
                            }
                    )
            }
    )
    @DeleteMapping("/{reviewId}")
    public ResponseEntity<String> deleteReview(@PathVariable String reviewId) {
        return ResponseEntity.ok(reviewService.deleteReview(reviewId));
    }

    @Operation(
            method = "GET",
            description = "Endpoint to fetch all the reviews posted its respective user",
            summary = "Endpoint to fetch all the reviews posted its respective user",
            parameters = {@Parameter(name = "", description = "No parameters required", required = false)},
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK",
                            content = {
                                    @Content(
                                            mediaType = "APPLICATION_JSON",
                                            examples = {
                                                    @ExampleObject(
                                                            value = """
                                                                    [
                                                                      {
                                                                        "id":"1d661ce1-d372-4406-bafc-8130a2192ac5",
                                                                        "movieTitle":"The Avengers endgame",
                                                                        "rating":4.0,
                                                                        "comment":"One of the best movie by Marvel cinematic universe",
                                                                        "postedOn":"2023-05-14T15:02:00"
                                                                      }
                                                                    ]
                                                                    """
                                                    )
                                            }
                                    )
                            }
                    )
            }
    )
    @GetMapping
    public ResponseEntity<List<ReviewResponse>> listAllReviewsOfLoggedUser(@RequestHeader(HttpHeaders.AUTHORIZATION) String bearer) {
        String username = jwtAuthService.extractUsername(bearer.substring(7));
        return ResponseEntity.ok(reviewService.getAllReviewsOfLoggedUser(username));
    }
}
