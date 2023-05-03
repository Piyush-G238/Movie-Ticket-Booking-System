package com.app.audienceize.services.interfaces;

import com.app.audienceize.dtos.requests.TicketRequest;
import com.app.audienceize.dtos.responses.TicketResponse;

import java.util.List;

public interface TicketService {
    public String bookTickets(TicketRequest request, String username);

    public List<TicketResponse> getPreviousTickets(String username);
}
