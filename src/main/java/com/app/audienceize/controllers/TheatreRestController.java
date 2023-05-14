package com.app.audienceize.controllers;

import com.app.audienceize.dtos.requests.TheatreRequest;
import com.app.audienceize.dtos.responses.TheatreResponse;
import com.app.audienceize.services.interfaces.TheatreService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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
@RequestMapping("/api/v1/theatres")
@Tag(name = "Theatre Rest Apis")
public class TheatreRestController {
    @Autowired
    private TheatreService theatreService;

    @Operation(
            method = "POST",
            description = "Endpoint to add a theatre in the application. Users with role ADMIN can only access this endpoint",
            summary = "Endpoint to add theatre in the application",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "CREATED",
                            content = {
                                    @Content(
                                            mediaType = "TEXT_PLAIN",
                                            examples = {
                                                    @ExampleObject(
                                                            value = "Theatre has been added to application"
                                                    )
                                            }
                                    )
                            }
                    )
            }
    )
    @PostMapping
    public ResponseEntity<String> addTheatre(@Valid @RequestBody TheatreRequest request) {
        String res = theatreService.addTheatre(request);
        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }
    @Operation(
            method = "GET",
            description = "Endpoint to fetch list of theatre by name of city",
            summary = "Endpoint to fetch list of theatre by name of city",
            parameters = {
                    @Parameter(
                            name = "city",
                            required = true,
                            example = "New Delhi"
                    )
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "CREATED",
                            content = {
                                    @Content(
                                            mediaType = "APPLICATION_JSON",
                                            examples = {
                                                    @ExampleObject(
                                                            value = """
                                                                    [
                                                                      {
                                                                        "id":"09b8611e-a41e-499d-bbf4-5833eccea745",
                                                                        "name":"PVR Select city walk",
                                                                        "city":"New Delhi",
                                                                        "address":"Select city walk, Saket, New Delhi"
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
    @GetMapping
    public ResponseEntity<List<TheatreResponse>> getTheatresByCity(@RequestParam(name = "city") String city) {
        return ResponseEntity.ok(theatreService.getTheatresByCity(city));
    }
    @Operation(
            method = "DELETE",
            description = "Endpoint to remove theatre from the application. Authenticated Users with role ADMIN can only access this endpoint.",
            summary = "Endpoint to remove theatre from the application.",
            responses = {
                    @ApiResponse(
                            content = {
                                    @Content(
                                            mediaType = "TEXT_PLAIN",
                                            examples = {
                                                    @ExampleObject(value = "Theatre is removed from the application.")
                                            }
                                    )
                            }
                    )
            }
    )
    @DeleteMapping
    public ResponseEntity<String> deleteTheatreByName(@RequestParam(name = "name") String name){
        return ResponseEntity.ok(theatreService.deleteTheatreByName(name));
    }
}
