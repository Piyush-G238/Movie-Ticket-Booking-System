package com.app.audienceize.services;

import com.app.audienceize.dtos.requests.MovieRequest;

public interface MovieService {
    public String addMovie(MovieRequest request);
}
