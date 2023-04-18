package com.app.audienceize.dtos.requests;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ReviewRequest {
    @NotEmpty(message = "MovieTitle must be there for posting review")
    private String movieTitle;

    @Min(value = 1, message = "Min rating should be 1")
    @Max(value = 5, message = "Max rating should be 5")
    private Double rating;

    @NotEmpty(message = "Comment cannot be empty for posting review")
    private String comment;
}
