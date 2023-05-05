package com.app.audienceize.services.impl;

import com.app.audienceize.dtos.requests.TheatreRequest;
import com.app.audienceize.dtos.responses.TheatreResponse;
import com.app.audienceize.entities.Theatre;
import com.app.audienceize.entities.TheatreSeat;
import com.app.audienceize.enums.SeatType;
import com.app.audienceize.repositories.TheatreSeatRepository;
import com.app.audienceize.services.interfaces.TheatreService;
import com.app.audienceize.repositories.TheatreRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class TheatreServiceImpl implements TheatreService {
    @Autowired
    private TheatreRepository theatreRepository;

    @Autowired
    private TheatreSeatRepository seatRepository;
    @Override
    public String addTheatre(TheatreRequest request) {
        Theatre theatre = toEntity(request);
        List<TheatreSeat> theatreSeats = getTheatreSeats();
        for (TheatreSeat seat: theatreSeats) {
            seat.setTheatre(theatre);
        }
        theatre.setTheatreSeats(theatreSeats);
        theatreRepository.save(theatre);
        return "Theatre has been added to application";
    }

    @Override
    public List<TheatreResponse> getTheatresByCity(String city) {
        List<Theatre> theatres = theatreRepository.findByCityIgnoreCase(city);
        if (theatres.size() == 0){
            throw new NoSuchElementException("There are no theatres available in this city");
        }
        return theatres.stream().map(this::toResponse).toList();
    }

    @Override
    @Transactional
    public String deleteTheatreByName(String name) {
        if (theatreRepository.existsByTheatreNameIgnoreCase(name)){
            theatreRepository.deleteByTheatreNameIgnoreCase(name);
            return "Theatre with given name has been removed from application";
        }
        throw new NoSuchElementException("Theatre with this name is currently not present");
    }

    private Theatre toEntity(TheatreRequest request) {
        return Theatre.builder()
                .theatreId(UUID.randomUUID().toString())
                .theatreName(request.getName())
                .city(request.getCity())
                .address(request.getAddress())
                .build();
    }

    private TheatreResponse toResponse(Theatre theatre){
        return TheatreResponse.builder()
                .id(theatre.getTheatreId())
                .name(theatre.getTheatreName())
                .city(theatre.getCity())
                .address(theatre.getAddress())
                .build();
    }

    private TheatreSeat getTheatreSeat(String seatNo, SeatType type) {
        return TheatreSeat.builder()
                .theatreSeatId(UUID.randomUUID().toString())
                .seatNumber(seatNo)
                .seatType(type)
                .build();
    }

    private List<TheatreSeat> getTheatreSeats() {
        List<TheatreSeat> seats = new ArrayList<>();
        seats.add(getTheatreSeat("1A", SeatType.REGULAR));
        seats.add(getTheatreSeat("1B", SeatType.REGULAR));
        seats.add(getTheatreSeat("1C", SeatType.REGULAR));
        seats.add(getTheatreSeat("1D", SeatType.REGULAR));
        seats.add(getTheatreSeat("1E", SeatType.REGULAR));

        seats.add(getTheatreSeat("2A", SeatType.RECLINER));
        seats.add(getTheatreSeat("2B", SeatType.RECLINER));
        seats.add(getTheatreSeat("2C", SeatType.RECLINER));
        seats.add(getTheatreSeat("2D", SeatType.RECLINER));
        seats.add(getTheatreSeat("2E", SeatType.RECLINER));
        seats = seatRepository.saveAll(seats);
        return seats;
    }
}
