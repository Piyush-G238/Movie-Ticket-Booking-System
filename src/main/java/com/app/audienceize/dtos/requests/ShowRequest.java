package com.app.audienceize.dtos.requests;

import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ShowRequest {
    @NotEmpty(message = "Movie title should be mentioned for adding a show.")
    private String movieTitle;
    @NotEmpty(message = "Timing should be there in order to add a show.")
    private String timing;
    @NotEmpty(message = "Theatre name should be there in order to add a show")
    private String theatreName;
}
