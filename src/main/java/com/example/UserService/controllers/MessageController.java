package com.example.UserService.controllers;

import com.example.UserService.models.Message;
import com.example.UserService.models.Order;
import com.example.UserService.models.OrderStatus;
import com.example.UserService.services.implementation.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.user.SimpUserRegistry;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
public class MessageController {

    private SimpMessagingTemplate simpMessagingTemplate;
    private OrderService orderService;

    @Autowired
    public MessageController(SimpMessagingTemplate simpMessagingTemplate, OrderService orderService) {
        this.simpMessagingTemplate = simpMessagingTemplate;
        this.orderService = orderService;
    }

    @MessageMapping("/send-message")
    @SendTo("/topic/orders")
    public Message send(@Payload Message message) throws Exception {
        System.out.println("send id: " + message.getId() + " status: " + message.getStatus());
        String time = new SimpleDateFormat("HH:mm").format(new Date());
        System.out.println("[" + time + "] Message sent.");
        //message.setStatus(orderService.getOrderStatus(message.getId()));

        return message;
    }

    @RequestMapping(method = RequestMethod.GET, path = "/test")
    public String test() {
        this.simpMessagingTemplate.convertAndSend("/topic/orders", new Message(1L, "PREPARING"));
        return "ok";
    }
}
