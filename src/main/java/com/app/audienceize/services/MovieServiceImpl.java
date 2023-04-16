package com.app.audienceize.services;

import com.app.audienceize.dtos.requests.MovieRequest;
import com.app.audienceize.entities.Movie;
import com.app.audienceize.repositories.MovieRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.UUID;

@Service
@Slf4j
public class MovieServiceImpl implements MovieService{
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

    private Movie toEntity(MovieRequest req) {
        return Movie.builder()
                .movieId(UUID.randomUUID().toString())
                .title(req.getTitle())
                .genre(req.getGenre())
                .releasedOn(LocalDate.parse(req.getReleasedOn()))
                .build();
    }
}
