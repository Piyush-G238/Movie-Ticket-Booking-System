package com.app.audienceize.services;

import com.app.audienceize.dtos.requests.TheatreRequest;
import com.app.audienceize.entities.Theatre;
import com.app.audienceize.repositories.TheatreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class TheatreServiceImpl implements TheatreService{
    @Autowired
    private TheatreRepository theatreRepository;

    @Override
    public String addTheatre(TheatreRequest request) {
        Theatre theatre = toEntity(request);
        theatreRepository.save(theatre);
        return "Theatre has been added to application";
    }

    private Theatre toEntity(TheatreRequest request) {
        return Theatre.builder()
                .theatreId(UUID.randomUUID().toString())
                .theatreName(request.getName())
                .city(request.getCity())
                .address(request.getAddress())
                .build();
    }
}
