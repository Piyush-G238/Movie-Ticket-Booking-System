package com.app.audienceize.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
@Tag(name = "Home")
public class HomeRestController {
    @GetMapping("/")
    @Operation(
            method = "GET",
            description = "This URI will describe the information about this REST application",
            summary = "This URI will describe the information about this REST application",
            responses = {
                    @ApiResponse(
                            description = "OK",
                            responseCode = "200",
                            content = {
                                    @Content(
                                            mediaType = "APPLICATION_JSON",
                                            examples = {
                                                    @ExampleObject(
                                                            value = """
                                                                    {
                                                                      "project-title" : "abc",
                                                                      "created-by" : "Piyush Gupta",
                                                                      "description" : "REST Application for booking movie tickets and reviewing movies",
                                                                    }"""
                                                    )
                                            }

                                    )
                            }
                    )
            }
    )

    public ResponseEntity<Map<String, String>> home() {
        Map<String, String> map = new HashMap<>();
        map.put("project-title", "Movie-ticket-booking-application");
        map.put("created-by", "Piyush Gupta");
        map.put("description", "REST Application for booking movie tickets and reviewing movies");
        map.put("timestamp", LocalDateTime.now().toString());
        return ResponseEntity.ok(map);
    }
}
