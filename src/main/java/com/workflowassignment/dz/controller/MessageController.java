package com.workflowassignment.dz.controller;

import com.workflowassignment.dz.Service.MessageService;
import com.workflowassignment.dz.model.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;


@Controller
public class MessageController {

    @Autowired
    private MessageService service;

    @MessageMapping("/message/auto-notification")
    @SendTo("/topic/public")
    public void autoNotification() throws Exception {
        service.startAutoNotification();
    }

    @MessageMapping("/message/sendMessage")
    @SendTo("/topic/public")
    public Message sendMessage(@Payload Message message) {
        return service.sendMessageAndStopAutoNotification(message);
    }

    @MessageMapping("/message/addUser")
    @SendTo("/topic/public")
    public Message addUser(@Payload Message message,
                           SimpMessageHeaderAccessor headerAccessor) {
        return service.addUser(message, headerAccessor);
    }

}
