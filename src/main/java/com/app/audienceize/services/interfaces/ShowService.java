package com.app.audienceize.services.interfaces;

import com.app.audienceize.dtos.requests.ShowRequest;
import com.app.audienceize.dtos.responses.ShowResponse;

import java.util.List;

public interface ShowService {
    public String addShow(ShowRequest showRequest);
    public List<ShowResponse> getShowsByTheatre(String theatreName);
}
