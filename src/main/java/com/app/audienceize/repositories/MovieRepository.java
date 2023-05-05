package com.app.audienceize.repositories;

import com.app.audienceize.entities.Movie;
import com.app.audienceize.enums.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MovieRepository extends JpaRepository<Movie, String> {
    void deleteByTitle(String title);
    List<Movie> findByGenre(Genre genre);
    Optional<Movie> findByTitle(String title);
    boolean existsByTitle(String title);
}
