package com.app.audienceize.controllers;

import com.app.audienceize.dtos.requests.ShowRequest;
import com.app.audienceize.dtos.responses.ShowResponse;
import com.app.audienceize.services.interfaces.ShowService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/shows")
@Tag(name = "Show Rest Apis")
public class ShowRestController {

    @Autowired
    private ShowService showService;

    @Operation(
            method = "POST",
            description = "Endpoint to create a Show of respective movie running in a theatre. This endpoint will only be accessed by user with Role ADMIN",
            summary = "Endpoint to create a Show of respective movie running in a theatre.",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "CREATED",
                            content = {
                                    @Content(
                                            mediaType = "TEXT_PLAIN",
                                            examples = {
                                                    @ExampleObject(value = "New show timing 10:00 has been added for movie The Avengers Endgame running at PVR Select City Walk.")
                                            }
                                    )
                            }
                    )
            }
    )
    @PostMapping
    public ResponseEntity<String> addShow(@Valid @RequestBody ShowRequest request) {
        String res = showService.addShow(request);
        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }

    @Operation(
            method = "GET",
            description = "Endpoint to get all the shows running in their respective Theatre",
            summary = "Endpoint to get all the shows running in their respective Theatre",
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
                                                                    [
                                                                      {
                                                                        "id":"09b8611f-a41e-499d-bbf4-5833eccea746",
                                                                        "timing":"2023-05-15T10:30:00"
                                                                        "creeatedAt":"2023-05-15T05:30:00",
                                                                        "updatedAt":"2023-05-15T05:30:00",
                                                                        "theatre":{
                                                                            "id":"09b8611e-a41e-499d-bbf4-5833eccea745",
                                                                            "name":"PVR Select city walk",
                                                                            "city":"New Delhi",
                                                                            "address":"Select city walk, Saket, New Delhi"
                                                                        },
                                                                        "movie":{
                                                                            "id":"09b8611f-a41e-499d-bbf4-5835eccea747",
                                                                            "title":"The Avengers endgame",
                                                                            "rating":4.5,
                                                                            "genre":"SCI_FI",
                                                                            "releasedOn":"2023-05-11",
                                                                            "length":150
                                                                        }
                                                                      }
                                                                    ]
                                                                    """
                                                    )
                                            }
                                    )
                            }
                    )
            }
    )
    @GetMapping("/theatres")
    public ResponseEntity<List<ShowResponse>> getShowsByTheatreName(@RequestParam("name") String name) {
        List<ShowResponse> responses = showService.getShowsByTheatre(name);
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }

    @Operation(
            method = "DELETE",
            description = "Endpoint to remove a running show from respective theatre.",
            summary = "Endpoint to remove a running show from respective theatre.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK",
                            content = {
                                    @Content(
                                            mediaType = "TEXT_PLAIN",
                                            examples = {
                                                    @ExampleObject(
                                                            value = "Your show has been removed from the application"
                                                    )
                                            }
                                    )
                            }
                    )
            }
    )
    @DeleteMapping("/{showId}")
    public ResponseEntity<String> removeShow(@PathVariable String showId) {
        return ResponseEntity.ok(showService.removeShow(showId));
    }
}
