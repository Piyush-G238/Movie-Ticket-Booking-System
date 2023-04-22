package com.app.audienceize.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalTime;
import java.util.Date;

@Getter
@Setter
@Entity
@Builder
@Table(name = "shows")
@NoArgsConstructor
@AllArgsConstructor
public class Show {
    @Id
    private String showId;
    private LocalTime timing;

    @CreationTimestamp
    private Date createdAt;

    @UpdateTimestamp
    private Date updatedAt;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "movie_id")
    private Movie movie;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "theatre_id")
    private Theatre theatre;
}