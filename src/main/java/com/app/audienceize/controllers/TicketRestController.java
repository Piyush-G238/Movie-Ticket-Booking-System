package com.app.audienceize.controllers;

import com.app.audienceize.dtos.requests.TicketRequest;
import com.app.audienceize.dtos.responses.TicketResponse;
import com.app.audienceize.services.impl.JwtAuthService;
import com.app.audienceize.services.interfaces.TicketService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/tickets")
@Tag(name = "Ticket Rest Apis")
public class TicketRestController {

    @Autowired
    private TicketService ticketService;

    @Autowired
    private JwtAuthService authService;

    @Operation(
            method = "POST",
            description = "Endpoint for booking tickets of particular show running in respective theatres",
            summary = "Endpoint for booking tickets of particular show running in respective theatres",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "CREATED",
                            content = {
                                    @Content(
                                            mediaType = "TEXT_PLAIN",
                                            examples = {
                                                    @ExampleObject(value = "Your tickets are booked successfully. Here is your ticket ID : 01a4c6c0-1d3a-4978-a3c4-d21b6c8227ea")
                                            }
                                    )
                            }
                    )
            }
    )
    @PostMapping
    public ResponseEntity<String> bookTickets(@Valid @RequestBody TicketRequest ticketRequest, @RequestHeader(HttpHeaders.AUTHORIZATION) String bearer) {
        String username = authService.extractUsername(bearer.substring(7));
        String response = ticketService.bookTickets(ticketRequest, username);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Operation(
            method = "GET",
            description = "Endpoint for fetching all the previous ticket booking done by respective Authenticated user",
            summary = "Endpoint for fetching all the previous ticket booking done by respective Authenticated user",
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
                                                                        "ticketId":"01a4c6c0-1d3a-4978-a3c4-d21b6c8227ea",
                                                                        "amount":652,
                                                                        "bookedAt":"2023-04-27 02:39:45.234",
                                                                        "allotedSeat":"1A 2A"
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
    public ResponseEntity<List<TicketResponse>> getPreviousTickets(@RequestHeader(HttpHeaders.AUTHORIZATION) String bearer){
        String username = authService.extractUsername(bearer.substring(7));
        List<TicketResponse> ticketResponses = ticketService.getPreviousTickets(username);
        return new ResponseEntity<>(ticketResponses, HttpStatus.OK);
    }
}
