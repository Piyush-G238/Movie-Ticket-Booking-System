package com.app.audienceize.services;

import com.app.audienceize.dtos.requests.ShowRequest;
import com.app.audienceize.dtos.responses.ShowResponse;

import java.util.List;

public interface ShowService {
    public String addShow(ShowRequest showRequest);
}
