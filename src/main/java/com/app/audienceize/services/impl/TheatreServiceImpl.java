package com.app.audienceize.services.impl;

import com.app.audienceize.dtos.requests.TheatreRequest;
import com.app.audienceize.dtos.responses.TheatreResponse;
import com.app.audienceize.entities.Theatre;
import com.app.audienceize.services.interfaces.TheatreService;
import com.app.audienceize.repositories.TheatreRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class TheatreServiceImpl implements TheatreService {
    @Autowired
    private TheatreRepository theatreRepository;

    @Override
    public String addTheatre(TheatreRequest request) {
        Theatre theatre = toEntity(request);
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
}
