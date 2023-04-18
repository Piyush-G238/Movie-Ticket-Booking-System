package com.app.audienceize.dtos.responses;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ReviewResponse {
    private String id;
    private String movieTitle;
    private Double rating;
    private String comment;
    private LocalDateTime postedDate;
}
