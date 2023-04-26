package com.app.audienceize.controllers.repositories;

import com.app.audienceize.entities.TheatreSeat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TheatreSeatRepository extends JpaRepository<TheatreSeat, String> {
}
