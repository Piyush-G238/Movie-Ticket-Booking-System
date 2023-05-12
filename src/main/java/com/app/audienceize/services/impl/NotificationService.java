package com.app.audienceize.services.impl;

import com.app.audienceize.dtos.responses.TicketMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {
    @Autowired
    private JavaMailSender javaMailSender;

    public void sendNotification(TicketMessage message) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(message.getEmail());
        mailMessage.setSubject(message.getMessage());
        mailMessage.setText("show details : "+ message.getShow()+"\n"+"Seats : "+ message.getSeats());
        javaMailSender.send(mailMessage);
    }
}
