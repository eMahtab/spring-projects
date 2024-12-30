package net.mahtabalam.producer;

import net.mahtabalam.model.Tweet;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
public class KafkaJsonProducer {

    private final KafkaTemplate<String, Tweet> kafkaTemplate;

    public KafkaJsonProducer(KafkaTemplate<String, Tweet> kafkaTemplate){
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(Tweet tweet) {
        Message<Tweet> message = MessageBuilder
                .withPayload(tweet)
                .setHeader(KafkaHeaders.TOPIC, "demo-topic")
                .build();

        kafkaTemplate.send(message);
    }
}