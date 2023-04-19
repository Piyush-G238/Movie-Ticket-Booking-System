package com.app.audienceize.repositories;

import com.app.audienceize.entities.Theatre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TheatreRepository extends JpaRepository<Theatre, String> {
    void deleteByTheatreNameIgnoreCase(String theatreName);
    boolean existsByTheatreNameIgnoreCase(String theatreName);
    List<Theatre> findByCityIgnoreCase(String city);
}
