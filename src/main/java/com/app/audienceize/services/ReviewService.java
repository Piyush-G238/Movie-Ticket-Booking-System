package com.app.audienceize.services;

import com.app.audienceize.dtos.requests.ReviewRequest;
import com.app.audienceize.dtos.responses.ReviewResponse;

import java.util.List;

public interface ReviewService {
    public String addReview(ReviewRequest request);
    public List<ReviewResponse> getTop5ReviewsByMovieTitle(String movieTitle);
}
