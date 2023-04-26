package com.app.audienceize.services.interfaces;

import com.app.audienceize.dtos.requests.TicketRequest;

public interface TicketService {
    public String bookTickets(TicketRequest request, String username);
}
