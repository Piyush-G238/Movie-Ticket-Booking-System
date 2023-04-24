package com.app.audienceize.entities;

import com.app.audienceize.enums.SeatType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "show_seats")
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

    @ManyToOne
    @JoinColumn(name = "show_id")
    private Show show;
}
