package com.app.audienceize.services;

import com.app.audienceize.dtos.requests.MovieRequest;
import com.app.audienceize.dtos.responses.MovieResponse;
import com.app.audienceize.enums.Genre;

import java.util.List;

public interface MovieService {
    public String addMovie(MovieRequest request);
    public MovieResponse getMovieByTitle(String title);
    public List<MovieResponse> getMoviesByGenre(Genre genre);
    public String deleteMovieByTitle(String title);
}
