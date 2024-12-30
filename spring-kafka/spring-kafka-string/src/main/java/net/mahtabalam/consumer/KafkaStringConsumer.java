package net.mahtabalam.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaStringConsumer {

    private static final Logger logger = LoggerFactory.getLogger(KafkaStringConsumer.class);

    @KafkaListener(topics = "test-topic", groupId = "testGroup")
    public void consumeMessage(String message) {
        logger.info("Consuming the message from test-topic={}", message);
    }
}