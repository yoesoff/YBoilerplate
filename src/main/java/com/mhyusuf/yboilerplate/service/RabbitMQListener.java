package com.mhyusuf.yboilerplate.service;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class RabbitMQListener {

    // Inject the queue name from the properties file
    @Value("${rabbitmq.queue.name}")
    private String queueName;

    // Method to listen for messages from the specified queue
    @RabbitListener(queues = "${rabbitmq.queue.name}")
    public void listen(String message) {
        System.out.println("Received message from queue " + queueName + ": " + message);
    }
}
