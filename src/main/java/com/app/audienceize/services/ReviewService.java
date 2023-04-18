package com.app.audienceize.services;

import com.app.audienceize.dtos.requests.ReviewRequest;

public interface ReviewService {
    public String addReview(ReviewRequest request);
}
