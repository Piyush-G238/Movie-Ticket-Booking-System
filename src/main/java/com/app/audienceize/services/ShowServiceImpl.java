package com.app.audienceize.services;

import com.app.audienceize.dtos.requests.ShowRequest;
import com.app.audienceize.entities.Movie;
import com.app.audienceize.entities.Show;
import com.app.audienceize.entities.Theatre;
import com.app.audienceize.repositories.MovieRepository;
import com.app.audienceize.repositories.ShowRepository;
import com.app.audienceize.repositories.TheatreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class ShowServiceImpl implements ShowService {
    @Autowired
    private ShowRepository showRepository;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private TheatreRepository theatreRepository;

    @Override
    public String addShow(ShowRequest showRequest) {
        Show show = toEntity(showRequest);
        Movie movie = movieRepository.findByTitle(showRequest.getMovieTitle())
                .orElseThrow(() -> new NoSuchElementException("No movie is present with this title."));

        Theatre theatre = theatreRepository.findByTheatreName(showRequest.getTheatreName())
                .orElseThrow(() -> new NoSuchElementException("No theatre is there with this name."));

        show.setMovie(movie);
        show.setTheatre(theatre);
        showRepository.save(show);
        return "New show timing (" + showRequest.getTiming() + ") has been for movie " + showRequest.getMovieTitle() + " running at " + showRequest.getTheatreName();
    }

    private Show toEntity(ShowRequest showRequest) {
        return Show.builder()
                .showId(UUID.randomUUID().toString())
                .timing(LocalTime.parse(showRequest.getTiming()))
                .build();
    }
}
