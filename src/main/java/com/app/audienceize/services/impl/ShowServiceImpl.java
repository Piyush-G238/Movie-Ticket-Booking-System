package com.app.audienceize.services.impl;

import com.app.audienceize.repositories.ShowSeatRepository;
import com.app.audienceize.dtos.requests.ShowRequest;
import com.app.audienceize.dtos.responses.MovieResponse;
import com.app.audienceize.dtos.responses.ShowResponse;
import com.app.audienceize.dtos.responses.TheatreResponse;
import com.app.audienceize.entities.*;
import com.app.audienceize.enums.SeatType;
import com.app.audienceize.services.interfaces.ShowService;
import com.app.audienceize.repositories.MovieRepository;
import com.app.audienceize.repositories.ShowRepository;
import com.app.audienceize.repositories.TheatreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;
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

    @Autowired
    private ShowSeatRepository showSeatRepository;
    @Override
    public String addShow(ShowRequest showRequest) {
        Show show = toEntity(showRequest);
        Movie movie = movieRepository.findByTitle(showRequest.getMovieTitle())
                .orElseThrow(() -> new NoSuchElementException("No movie is present with this title."));

        Theatre theatre = theatreRepository.findByTheatreName(showRequest.getTheatreName())
                .orElseThrow(() -> new NoSuchElementException("No theatre is there with this name."));

        show.setMovie(movie);
        show.setTheatre(theatre);
        List<ShowSeat> showSeats = theatre.getTheatreSeats().stream().map(this::getShowSeat).toList();
        for (ShowSeat seatEntity : showSeats) {
            seatEntity.setShow(show);
        }
        show.setSeats(showSeats);
        showRepository.save(show);
        return "New show timing (" + showRequest.getTiming() + ") has been added for movie " + showRequest.getMovieTitle() + " running at " + showRequest.getTheatreName();
    }

    @Override
    public List<ShowResponse> getShowsByTheatre(String theatreName) {
       Theatre theatre = theatreRepository
               .findByTheatreName(theatreName)
               .orElseThrow(()->new NoSuchElementException("Theatre not available."));
        List<Show> runningShows = showRepository.findByTheatre(theatre);
        return runningShows.stream().map(this::toResponse).toList();
    }

    @Override
    public String removeShow(String showId) {
        if(showRepository.existsById(showId)) {
            showRepository.deleteById(showId);
            return "Your show has been removed from the application";
        }
        throw new NoSuchElementException("This show is not available in the application");
    }

    private Show toEntity(ShowRequest showRequest) {
        return Show.builder()
                .showId(UUID.randomUUID().toString())
                .createdAt(new Date())
                .timing(LocalDateTime.parse(showRequest.getTiming()))
                .build();
    }

    private ShowResponse toResponse(Show show) {
        return ShowResponse.builder()
                .id(show.getShowId())
                .timing(show.getTiming())
                .createdAt(show.getCreatedAt())
                .updatedAt(show.getUpdatedAt())
                .theatre(toTheatreResponse(show.getTheatre()))
                .movie(toMovieResponse(show.getMovie()))
                .build();
    }

    private TheatreResponse toTheatreResponse(Theatre theatre) {
        return TheatreResponse.builder()
                .id(theatre.getTheatreId())
                .name(theatre.getTheatreName())
                .city(theatre.getCity())
                .address(theatre.getAddress())
                .build();
    }

    private MovieResponse toMovieResponse(Movie movie) {
        return MovieResponse.builder()
                .id(movie.getMovieId())
                .title(movie.getTitle())
                .rating(movie.getRatings())
                .genre(movie.getGenre())
                .releasedOn(movie.getReleasedOn())
                .length(movie.getLength())
                .build();
    }

    private ShowSeat getShowSeat(TheatreSeat seat) {
        ShowSeat showSeat = ShowSeat.builder()
                .showSeatId(UUID.randomUUID().toString())
                .seatNumber(seat.getSeatNumber())
                .seatType(seat.getSeatType())
                .build();
        if(seat.getSeatType() == SeatType.REGULAR){
            showSeat.setRate(251.50);
        } else if (seat.getSeatType() == SeatType.RECLINER) {
            showSeat.setRate(400.50);
        }
        return showSeat;
    }
}
