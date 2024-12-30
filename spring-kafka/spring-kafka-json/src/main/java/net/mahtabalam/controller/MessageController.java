package net.mahtabalam.controller;

import net.mahtabalam.model.Tweet;
import net.mahtabalam.producer.KafkaJsonProducer;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/messages")
public class MessageController {

    private final KafkaJsonProducer kafkaJsonProducer;

    public MessageController(KafkaJsonProducer kafkaJsonProducer){
        this.kafkaJsonProducer = kafkaJsonProducer;
    }

    @PostMapping
    public ResponseEntity<String> sendJsonMessage(@RequestBody Tweet tweet) {
        kafkaJsonProducer.sendMessage(tweet);
        return ResponseEntity.ok("Message queued successfully as JSON");
    }
}