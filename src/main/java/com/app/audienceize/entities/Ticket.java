package com.app.audienceize.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@Builder
@Entity
@Table(name = "tickets")
@NoArgsConstructor
@AllArgsConstructor
public class Ticket {
    @Id
    private String ticketId;
    private String allotedSeat;
    private Double amount;

    @CreationTimestamp
    private Date bookedAt;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "ticket",cascade = CascadeType.ALL)
    private List<ShowSeat> seats;
}
