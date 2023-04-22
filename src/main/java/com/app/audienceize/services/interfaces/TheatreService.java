package com.app.audienceize.services.interfaces;

import com.app.audienceize.dtos.requests.TheatreRequest;
import com.app.audienceize.dtos.responses.TheatreResponse;

import java.util.List;

public interface TheatreService {
    public String addTheatre(TheatreRequest request);
    public List<TheatreResponse> getTheatresByCity(String city);
    public String deleteTheatreByName(String name);
}
