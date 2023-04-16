package com.app.audienceize.controllers;

import com.app.audienceize.dtos.requests.MovieRequest;
import com.app.audienceize.dtos.responses.MovieResponse;
import com.app.audienceize.enums.Genre;
import com.app.audienceize.services.MovieService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/movies")
public class MovieRestController {
    @Autowired
    private MovieService movieService;

    @GetMapping("/test")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("GET request on /test endpoint works");
    }

    @PostMapping
    public ResponseEntity<String> addMovie(@Valid @RequestBody MovieRequest request) {
        String body = movieService.addMovie(request);
        if (body.contains("already exists.")) {
            return new ResponseEntity<>(body, HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(body, HttpStatus.CREATED);
    }

    @GetMapping("/{title}")
    public ResponseEntity<MovieResponse> getMovieByTitle(@PathVariable String title) {
        MovieResponse body = movieService.getMovieByTitle(title);
        return ResponseEntity.ok(body);
    }

    @GetMapping
    public ResponseEntity<List<MovieResponse>> getMoviesByGenre(@RequestParam(name = "genre") Genre genre) {
        List<MovieResponse> body = movieService.getMoviesByGenre(genre);
        if (body.size() == 0)
            return new ResponseEntity<>(body, HttpStatusCode.valueOf(404));
        return ResponseEntity.ok(body);
    }
    @DeleteMapping("/{title}")
    public ResponseEntity<String> deleteMovieByTitle(@PathVariable(name = "title") String title) {
        String body = movieService.deleteMovieByTitle(title);
        return ResponseEntity.ok(body);
    }
}
