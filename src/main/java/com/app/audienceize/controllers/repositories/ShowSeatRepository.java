package com.app.audienceize.controllers.repositories;

import com.app.audienceize.entities.ShowSeat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShowSeatRepository extends JpaRepository<ShowSeat, String> {
}
