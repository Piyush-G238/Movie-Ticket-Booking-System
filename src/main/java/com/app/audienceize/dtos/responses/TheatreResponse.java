package com.app.audienceize.dtos.responses;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TheatreResponse {
    private String id;
    private String name;
    private String city;
    private String address;
}
