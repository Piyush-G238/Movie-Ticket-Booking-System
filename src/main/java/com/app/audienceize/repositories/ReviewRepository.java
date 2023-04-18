package com.app.audienceize.repositories;

import com.app.audienceize.entities.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, String> {
    List<Review> findByMovieTitle(String movieTitle);
}
