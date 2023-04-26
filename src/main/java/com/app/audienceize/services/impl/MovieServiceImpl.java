package com.app.audienceize.services.impl;

import com.app.audienceize.dtos.requests.MovieRequest;
import com.app.audienceize.dtos.responses.MovieResponse;
import com.app.audienceize.entities.Movie;
import com.app.audienceize.enums.Genre;
import com.app.audienceize.controllers.repositories.MovieRepository;
import com.app.audienceize.services.interfaces.MovieService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
@Slf4j
public class MovieServiceImpl implements MovieService {
    @Autowired
    private MovieRepository movieRepository;

    @Override
    public String addMovie(MovieRequest request) {
        Movie movie = this.toEntity(request);
        if (movieRepository.existsByTitle(movie.getTitle())){
            log.info("{} already exists", movie.getTitle());
            return movie.getTitle()+" already exists.";
        }
        movieRepository.save(movie);
        log.info("{} is added", movie.getTitle());
        return movie.getTitle()+" is added.";
    }

    @Override
    public MovieResponse getMovieByTitle(String title) {
        Movie movie = movieRepository.findByTitle(title).orElseThrow(NoSuchElementException::new);
        return toResponse(movie);
    }

    @Override
    public List<MovieResponse> getMoviesByGenre(Genre genre) {
        List<Movie> movies = movieRepository.findByGenre(genre);
        return movies.stream().map(this::toResponse).toList();
    }

    @Override
    @Transactional
    public String deleteMovieByTitle(String title) {
        if (movieRepository.existsByTitle(title)){
            movieRepository.deleteByTitle(title);
            return title+" is removed.";
        }
        return "This Movie is currently not present";
    }

    private Movie toEntity(MovieRequest req) {
        return Movie.builder()
                .movieId(UUID.randomUUID().toString())
                .title(req.getTitle())
                .genre(req.getGenre())
                .releasedOn(LocalDate.parse(req.getReleasedOn()))
                .build();
    }

    private MovieResponse toResponse(Movie movie) {
        return MovieResponse.builder()
                .id(movie.getMovieId())
                .title(movie.getTitle())
                .genre(movie.getGenre())
                .rating(movie.getRatings())
                .releasedOn(movie.getReleasedOn())
                .build();
    }
}
