package com.mhyusuf.yboilerplate.kafka.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class MessageConsumer {

    @KafkaListener(topics = "yboiler-topic", groupId = "yboiler-group")
    public void consume(String message) {
        System.out.println("📩 Received message: " + message);
    }
}
