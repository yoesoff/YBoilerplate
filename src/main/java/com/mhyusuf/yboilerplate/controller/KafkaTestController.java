package com.mhyusuf.yboilerplate.controller;


import com.mhyusuf.yboilerplate.model.kafka.MessageObject;
import com.mhyusuf.yboilerplate.service.MessageProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;

@RestController
@RequestMapping("/kafka")
public class KafkaTestController {

    @Autowired
    private MessageProducer messageProducer;

    @PostMapping("/publish")
    public String publishMessage(@RequestParam("content") String content, @RequestParam("sender") String sender) {
        MessageObject message = new MessageObject();
        message.setId(String.valueOf(Instant.now().toEpochMilli()));
        message.setContent(content);
        message.setSender(sender);
        message.setTimestamp(Instant.now().toEpochMilli());

        messageProducer.sendMessage(message);
        return "Message published successfully!";
    }
}