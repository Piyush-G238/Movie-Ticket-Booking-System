package com.app.audienceize.controllers;

import com.app.audienceize.dtos.requests.TicketRequest;
import com.app.audienceize.dtos.responses.TicketResponse;
import com.app.audienceize.services.impl.JwtAuthService;
import com.app.audienceize.services.interfaces.TicketService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/tickets")
public class TicketRestController {

    @Autowired
    private TicketService ticketService;

    @Autowired
    private JwtAuthService authService;

    @PostMapping
    public ResponseEntity<String> bookTickets(@Valid @RequestBody TicketRequest ticketRequest, @RequestHeader(HttpHeaders.AUTHORIZATION) String bearer) {
        String username = authService.extractUsername(bearer.substring(7));
        String response = ticketService.bookTickets(ticketRequest, username);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<TicketResponse>> getPreviousTickets(@RequestHeader(HttpHeaders.AUTHORIZATION) String bearer){
        String username = authService.extractUsername(bearer.substring(7));
        List<TicketResponse> ticketResponses = ticketService.getPreviousTickets(username);
        return new ResponseEntity<>(ticketResponses, HttpStatus.OK);
    }
}
