package com.app.audienceize.dtos.responses;

import com.app.audienceize.entities.Show;
import com.app.audienceize.entities.ShowSeat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TicketMessage {
    private String email;
    private String message;
    private Show show;
    private List<ShowSeat> seats;
}
