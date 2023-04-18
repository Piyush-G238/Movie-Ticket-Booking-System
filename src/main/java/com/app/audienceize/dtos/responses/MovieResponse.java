package com.app.audienceize.dtos.responses;

import com.app.audienceize.enums.Genre;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class MovieResponse {
    private String id;
    private String title;
    private Genre genre;
    private Double rating;
    private LocalDate releasedOn;
}
