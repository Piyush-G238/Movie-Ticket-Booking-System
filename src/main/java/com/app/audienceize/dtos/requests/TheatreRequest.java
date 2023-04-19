package com.app.audienceize.dtos.requests;

import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TheatreRequest {
    @NotEmpty(message = "Theatre name should be mentioned.")
    private String name;

    @NotEmpty(message = "City should be there to locate theatre.")
    private String city;

    @NotEmpty(message = "Address should be mentioned.")
    private String address;
}
