package com.app.audienceize.dtos.responses;

import lombok.Builder;
import lombok.Data;

import java.time.LocalTime;
import java.util.Date;

@Data
@Builder
public class ShowResponse {
    private String id;
    private LocalTime timing;
    private Date createdAt;
    private Date updatedAt;
    private MovieResponse movie;
    private TheatreResponse theatre;
}
