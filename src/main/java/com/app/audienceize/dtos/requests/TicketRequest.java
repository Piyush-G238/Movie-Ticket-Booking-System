package com.app.audienceize.dtos.requests;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TicketRequest {
    private String allotedSeat;
    private ShowRequest show;

}
