package com.app.audienceize.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "tickets")
public class Ticket {
    @Id
    private String ticketId;
    private String allotedSeats;
    private Double amount;
    private Date bookedAt;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany
    private List<ShowSeat> seats;
}
