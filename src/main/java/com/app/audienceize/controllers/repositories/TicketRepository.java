package com.app.audienceize.controllers.repositories;

import com.app.audienceize.entities.Ticket;
import com.app.audienceize.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, String> {
    List<Ticket> findByUser(User user);
}
