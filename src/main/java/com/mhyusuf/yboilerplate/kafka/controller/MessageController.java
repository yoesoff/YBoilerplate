package com.mhyusuf.yboilerplate.kafka.controller;

import com.mhyusuf.yboilerplate.kafka.producer.MessageProducer;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/messages")
public class MessageController {

    private final MessageProducer producer;

    public MessageController(MessageProducer producer) {
        this.producer = producer;
    }

    @PostMapping
    public String sendMessage(@RequestParam String msg) {
        producer.sendMessage(msg);
        return "Message sent to Kafka topic!";
    }
}
