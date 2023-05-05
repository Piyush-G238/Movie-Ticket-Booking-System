package com.app.audienceize.dtos.responses;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@Builder
public class ShowResponse {
    private String id;
    private LocalDateTime timing;
    private Date createdAt;
    private Date updatedAt;
    private MovieResponse movie;
    private TheatreResponse theatre;
}
