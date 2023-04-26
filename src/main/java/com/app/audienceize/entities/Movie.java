package com.app.audienceize.entities;

import com.app.audienceize.enums.Genre;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
@Setter
@Entity
@Table(name = "movies")
@NoArgsConstructor
@AllArgsConstructor
public class Movie {
    @Id
    private String movieId;

    @Column(unique = true)
    private String title;
    private Double ratings;

    @Enumerated(EnumType.STRING)
    @Column(name = "genre", nullable = false)
    private Genre genre;

    private LocalDate releasedOn;

    private Long length;
    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL)
    private List<Review> reviews;

    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL)
    private List<Show> shows;

    public void addReview(Review review) {
        if (reviews == null)
            reviews = new ArrayList<>();
        reviews.add(review);
    }
}
