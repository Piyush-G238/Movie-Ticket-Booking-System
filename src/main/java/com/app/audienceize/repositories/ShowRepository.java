package com.app.audienceize.repositories;

import com.app.audienceize.entities.Show;
import com.app.audienceize.entities.Theatre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShowRepository extends JpaRepository<Show, String> {
    List<Show> findByTheatre(Theatre theatre);
}
