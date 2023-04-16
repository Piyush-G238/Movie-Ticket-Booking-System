package com.app.audienceize.repositories;

import com.app.audienceize.entities.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MovieRepository extends JpaRepository<Movie, String> {
    Optional<Movie> findByTitle(String title);
    boolean existsByTitle(String title);
}
