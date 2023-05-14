package com.app.audienceize.controllers;

import com.app.audienceize.dtos.requests.MovieRequest;
import com.app.audienceize.dtos.responses.MovieResponse;
import com.app.audienceize.enums.Genre;
import com.app.audienceize.services.interfaces.MovieService;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/movies")
@Tag(name = "Movie Rest Apis")
public class MovieRestController {
    @Autowired
    private MovieService movieService;

    @GetMapping("/test")
    @Hidden
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("GET request on /test endpoint works");
    }

    @PostMapping
    @Operation(
            method = "POST",
            description = "This URI will create a new Movie Resource in the application",
            summary = "This URI will create a new Movie Resource in the application",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "CREATED",
                            content = {
                                    @Content(
                                            mediaType = "TEXT_PLAIN",
                                            examples = {
                                                    @ExampleObject(
                                                            value = "The Avengers Endgame is added."
                                                    )
                                            }
                                    )
                            }
                    )
            }
    )
    public ResponseEntity<String> addMovie(@Valid @RequestBody MovieRequest request) {
        String body = movieService.addMovie(request);
        if (body.contains("already exists.")) {
            return new ResponseEntity<>(body, HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(body, HttpStatus.CREATED);
    }

    @Operation(
            method = "GET",
            description = "This URI will fetch movie resource by movie title",
            summary = "This URI will fetch movie resource by movie title",
            parameters = {
                    @Parameter(
                            name = "title",
                            required = true,
                            example = "The Avengers Endgame"
                    )
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK",
                            content = {
                                    @Content(
                                            mediaType = "APPLICATION_JSON",
                                            examples = {
                                                    @ExampleObject(
                                                            value = """
                                                                    {
                                                                      "id":"b110caf1-b0e5-47cb-8c83-8f47bd54586f",
                                                                      "title":"The Avengers Endgame",
                                                                      "genre":"SCI_FI",
                                                                      "rating":4.5,
                                                                      "releasedOn":"2023-05-11",
                                                                      "length":150
                                                                    }
                                                                    """
                                                    )
                                            }
                                    )
                            }
                    )
            }
    )
    @GetMapping
    public ResponseEntity<MovieResponse> getMovieByTitle(@RequestParam(name = "title") String title) {
        MovieResponse body = movieService.getMovieByTitle(title);
        return ResponseEntity.ok(body);
    }

    @Operation(
            method = "GET",
            parameters = {
                    @Parameter(
                            name = "name",
                            required = true,
                            example = "SCI_FI"
                    )
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK",
                            content = {
                                    @Content(
                                            mediaType = "APPLICATION_JSON",
                                            examples = {
                                                    @ExampleObject(
                                                            value = """
                                                                    {
                                                                      "id":"b110caf1-b0e5-47cb-8c83-8f47bd54586f",
                                                                      "title":"The Avengers Endgame",
                                                                      "genre":"SCI_FI",
                                                                      "rating":4.5,
                                                                      "releasedOn":"2023-05-11",
                                                                      "length":150
                                                                    }
                                                                    """
                                                    )
                                            }
                                    )
                            }
                    )
            }
    )
    @GetMapping("/genre")
    public ResponseEntity<List<MovieResponse>> getMoviesByGenre(@RequestParam(name = "name") Genre name) {
        List<MovieResponse> body = movieService.getMoviesByGenre(name);
        if (body.size() == 0)
            return new ResponseEntity<>(body, HttpStatusCode.valueOf(404));
        return ResponseEntity.ok(body);
    }

    @Operation(
            method = "DELETE",
            parameters = {
                    @Parameter(
                            name = "title",
                            required = true,
                            example = "The Avengers Endgame"
                    )
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK",
                            content = {
                                    @Content(
                                            mediaType = "TEXT_PLAIN",
                                            examples = {
                                                    @ExampleObject(
                                                            value = "The Avengers Endgame is removed."
                                                    )
                                            }
                                    )
                            }
                    )
            }
    )
    @DeleteMapping
    public ResponseEntity<String> deleteMovieByTitle(@RequestParam(name = "title") String title) {
        String body = movieService.deleteMovieByTitle(title);
        return ResponseEntity.ok(body);
    }
}
