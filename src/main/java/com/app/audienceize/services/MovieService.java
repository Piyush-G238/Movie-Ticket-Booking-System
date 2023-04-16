package com.app.audienceize.services;

import com.app.audienceize.dtos.requests.MovieRequest;
import com.app.audienceize.dtos.responses.MovieResponse;

public interface MovieService {
    public String addMovie(MovieRequest request);
    public MovieResponse getMovieByTitle(String title);
}
