package com.mhyusuf.yboilerplate.service;

import com.mhyusuf.yboilerplate.model.kafka.MessageObject;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

/**
 * Penjelasan:
 * @KafkaListener: Mendengarkan topik my_topic dan menerima pesan dari Kafka.
 * consume: Method yang menerima MessageObject dan menampilkannya di konsol.
 */
@Service
public class MessageConsumer {

    @KafkaListener(topics = "my_topic", groupId = "my-group")
    public void consume(MessageObject message) {
        System.out.println("Consumed message: " + message.toString());
    }
}