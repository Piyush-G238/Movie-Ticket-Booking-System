package com.app.audienceize.controllers;

import com.app.audienceize.dtos.requests.ShowRequest;
import com.app.audienceize.dtos.responses.ShowResponse;
import com.app.audienceize.services.interfaces.ShowService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/shows")
public class ShowRestController {

    @Autowired
    private ShowService showService;

    @PostMapping
    public ResponseEntity<String> addShow(@Valid @RequestBody ShowRequest request) {
        String res = showService.addShow(request);
        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }

    @GetMapping("/theatres")
    public ResponseEntity<List<ShowResponse>> getShowsByTheatreName(@RequestParam("name") String name){
        List<ShowResponse> responses = showService.getShowsByTheatre(name);
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }

    @GetMapping("/movies")
    public ResponseEntity<?> getShowsByMovieNameAndCity(@RequestParam("name") String name, @RequestParam("city") String city) {
        return null;
    }
}
