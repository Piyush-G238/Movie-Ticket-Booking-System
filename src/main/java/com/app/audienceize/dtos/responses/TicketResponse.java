package com.app.audienceize.dtos.responses;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class TicketResponse {
    private String ticketId;
    private String allotedSeat;
    private Double amount;
    private Date bookedAt;
}
