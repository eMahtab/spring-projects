package net.mahtabalam.consumer;

import net.mahtabalam.model.Tweet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaJsonConsumer {

    private static final Logger logger = LoggerFactory.getLogger(KafkaJsonConsumer.class);

    @KafkaListener(topics = "demo-topic", groupId = "testGroup")
    public void consumeJsonMessage(Tweet tweet) {
        logger.info("Consuming the message from demo-topic={}", tweet.toString());
    }
}