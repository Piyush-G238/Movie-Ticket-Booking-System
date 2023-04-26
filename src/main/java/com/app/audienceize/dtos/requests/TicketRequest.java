package com.app.audienceize.dtos.requests;

import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class TicketRequest {
    @NotEmpty(message = "Seat Numbers cannot be empty")
    private Set<String> seatNumbers;
    @NotEmpty(message = "show id must be mentioned in order to create a movie")
    private String showId;
}
