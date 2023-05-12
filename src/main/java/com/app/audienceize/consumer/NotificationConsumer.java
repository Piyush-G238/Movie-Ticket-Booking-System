package com.app.audienceize.consumer;

import com.app.audienceize.dtos.responses.TicketMessage;
import com.app.audienceize.services.impl.NotificationService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class NotificationConsumer {
    private final ObjectMapper mapper = new ObjectMapper();
    @Autowired
    private NotificationService service;

    @KafkaListener(topics = {"ticket_notifications"}, groupId = "ticketGrp")
    public void sendNotification(String notification) throws JsonProcessingException {
        TicketMessage message = mapper.readValue(notification, TicketMessage.class);
        service.sendNotification(message);
    }
}
