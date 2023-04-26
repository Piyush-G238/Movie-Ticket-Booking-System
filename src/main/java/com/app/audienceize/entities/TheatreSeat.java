package com.app.audienceize.entities;

import com.app.audienceize.enums.SeatType;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@Entity
@Table(name = "theatre_seat")
@NoArgsConstructor
@AllArgsConstructor
public class TheatreSeat {
    @Id
    private String theatreSeatId;
    private String seatNumber;

    @Enumerated(EnumType.STRING)
    private SeatType seatType;

    @ManyToOne
    @JoinColumn(name = "theatre_id")
    private Theatre theatre;
}
