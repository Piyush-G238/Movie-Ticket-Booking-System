package com.app.audienceize.services.interfaces;

import com.app.audienceize.dtos.requests.ReviewRequest;
import com.app.audienceize.dtos.responses.ReviewResponse;

import java.util.List;

public interface ReviewService {
    public String addReview(ReviewRequest request, String username);
    public List<ReviewResponse> getTop5ReviewsByMovieTitle(String movieTitle);
    public String updateReview(ReviewRequest request, String reviewId);
    public String deleteReview(String reviewId);
    public List<ReviewResponse> getAllReviewsOfLoggedUser(String username);
}
