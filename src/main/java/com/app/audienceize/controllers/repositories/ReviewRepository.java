package com.app.audienceize.controllers.repositories;

import com.app.audienceize.entities.Review;
import com.app.audienceize.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, String> {
    List<Review> findByUser(User user);
    List<Review> findByMovieTitle(String movieTitle);
}
