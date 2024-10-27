package com.mhyusuf.yboilerplate.service;
import com.mhyusuf.yboilerplate.model.kafka.MessageObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

/**
 * Penjelasan:
 * KafkaTemplate digunakan untuk mengirimkan objek MessageObject ke topik Kafka.
 * TOPIC: Nama topik tempat pesan akan dikirim, dalam hal ini my_topic.
 */
@Service
public class MessageProducer {

    private static final String TOPIC = "my_topic";

    @Autowired
    private KafkaTemplate<String, MessageObject> kafkaTemplate;

    public void sendMessage(MessageObject message) {
        kafkaTemplate.send(TOPIC, message);
        System.out.println("Sent message: " + message.toString());
    }
}