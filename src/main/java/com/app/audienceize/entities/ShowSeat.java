package com.app.audienceize.entities;

import com.app.audienceize.enums.SeatType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@Getter
@Setter
@Builder
@Entity
@Table(name = "show_seats")
@NoArgsConstructor
@AllArgsConstructor
public class ShowSeat {
    @Id
    private String showSeatId;
    private String seatNumber;
    private Double rate;
    private boolean isBooked;

    @CreationTimestamp
    private Date bookedAt;

    @Enumerated(EnumType.STRING)
    private SeatType seatType;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ticket_id")
    @JsonIgnore
    private Ticket ticket;
    @ManyToOne
    @JoinColumn(name = "show_id")
    @JsonIgnore
    private Show show;

    @Override
    public String toString() {
        return "ShowSeat{" +
                "showSeatId='" + showSeatId + '\'' +
                ", seatNumber='" + seatNumber + '\'' +
                ", rate=" + rate +
                ", bookedAt=" + bookedAt +
                ", seatType=" + seatType +
                '}';
    }
}
