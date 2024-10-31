package com.mhyusuf.yboilerplate.controller;

import com.mhyusuf.yboilerplate.service.RabbitMQSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rabbitmq")
public class RabbitMQController {

    @Autowired
    private RabbitMQSender rabbitMQSender;

    // Endpoint untuk mengirim pesan
    @PostMapping("/send")
    public String sendMessage(@RequestBody String message) {
        rabbitMQSender.send(message);
        return "Message sent to RabbitMQ: " + message;
    }
}
