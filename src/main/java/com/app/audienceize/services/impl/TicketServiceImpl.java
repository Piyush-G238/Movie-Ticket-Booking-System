package com.app.audienceize.services.impl;

import com.app.audienceize.dtos.requests.TicketRequest;
import com.app.audienceize.dtos.responses.TicketMessage;
import com.app.audienceize.dtos.responses.TicketResponse;
import com.app.audienceize.entities.Show;
import com.app.audienceize.entities.ShowSeat;
import com.app.audienceize.entities.Ticket;
import com.app.audienceize.entities.User;
import com.app.audienceize.repositories.ShowRepository;
import com.app.audienceize.repositories.TicketRepository;
import com.app.audienceize.repositories.UserRepository;
import com.app.audienceize.services.interfaces.TicketService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TicketServiceImpl implements TicketService {
    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private ShowRepository showRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public String bookTickets(TicketRequest request, String username) {
        String showId = request.getShowId();
        Show requiredShow = showRepository.findById(showId).orElseThrow(() -> new NoSuchElementException("Invalid showId is mentioned. Please try again"));
        List<ShowSeat> requiredSeats = requiredShow.getSeats();
        List<ShowSeat> filteredSeat = requiredSeats.stream().filter(seat -> {
            return !seat.isBooked() && request.getSeatNumbers().contains(seat.getSeatNumber());
        }).toList();
        if (filteredSeat.size() != request.getSeatNumbers().size()) {
            throw new NoSuchElementException("Seats are not available for bookings");
        }
        Ticket ticket = toEntity(request);
        Double amount = 0.0;
        String allotedSeats = "";
        for (ShowSeat seat : filteredSeat) {
            amount += seat.getRate();
            allotedSeats += seat.getSeatNumber() + " ";
        }
        ticket.setAmount(amount);
        ticket.setAllotedSeat(allotedSeats);
        ticket.setSeats(filteredSeat);
        ticket.setUser(userRepository.findByEmailId(username).get());
        Iterator<ShowSeat> showSeatItr = requiredSeats.iterator();
        while (showSeatItr.hasNext()) {
            ShowSeat seat = showSeatItr.next();
            if (filteredSeat.contains(seat)) {
                seat.setBooked(true);
                seat.setBookedAt(new Date());
                seat.setTicket(ticket);
            }
        }
        ticketRepository.save(ticket);
        requiredShow.setSeats(requiredSeats);
        showRepository.save(requiredShow);
        try {
            TicketMessage message = TicketMessage.builder()
                    .email(username)
                    .message("Tickets booked successfully. Ticket ID : " + ticket.getTicketId())
                    .show(requiredShow)
                    .seats(filteredSeat)
                    .build();
            kafkaTemplate.send("ticket_notifications", "notification",mapper.writeValueAsString(message));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "Your tickets are booked successfully. Here is your ticket ID : " + ticket.getTicketId();
    }

    @Override
    public List<TicketResponse> getPreviousTickets(String username) {
        User user = userRepository.findByEmailId(username).get();
        List<Ticket> ticketList = ticketRepository.findByUser(user);
        return ticketList.stream().map(this::ticketResponse).toList();
    }

    public Ticket toEntity(TicketRequest request) {
        return Ticket.builder()
                .ticketId(UUID.randomUUID().toString())
                .build();
    }

    public TicketResponse ticketResponse(Ticket ticket) {
        return TicketResponse.builder()
                .ticketId(ticket.getTicketId())
                .allotedSeat(ticket.getAllotedSeat())
                .amount(ticket.getAmount())
                .bookedAt(ticket.getBookedAt())
                .build();
    }
}
