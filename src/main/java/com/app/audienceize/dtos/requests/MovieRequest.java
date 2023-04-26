package com.app.audienceize.dtos.requests;

import com.app.audienceize.enums.Genre;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MovieRequest {
    @NotEmpty(message = "Title of the movie must be mentioned")
    private String title;

    @NotNull(message = "Genre of the movie must be mentioned")
    private Genre genre;

    @NotEmpty(message = "Released date of the movie must be mentioned")
    private String releasedOn;

    @Min(value = 60, message = "Minimum length of movie must be 60 minutes")
    private Long length;
}
