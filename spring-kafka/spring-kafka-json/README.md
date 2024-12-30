# Producing & Consuming JSON messages with Spring Kafka

This demo project is based on https://www.youtube.com/watch?v=KQDTtvZMS9c&t=1988s , in which a producer sends messages to demo-topic and a consumer consumes those messages.
The messages are sent in JSON format. Make sure Kafka server is running on default port 9092, before running the application.

## Project :

!["Spring Kafka Project"](images/project.png?raw=true)

## Messages API

```java
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
```

!["Messages API"](images/messages-api.png?raw=true)

## Producer

```java
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
```

## Consumer

```java
@Service
public class KafkaJsonConsumer {

    private static final Logger logger = LoggerFactory.getLogger(KafkaJsonConsumer.class);

    @KafkaListener(topics = "demo-topic", groupId = "testGroup")
    public void consumeJsonMessage(Tweet tweet) {
        logger.info("Consuming the message from demo-topic={}", tweet.toString());
    }
}
```

## application.yml

```yml
spring:
  application:
    name: spring-kafka-json
  kafka:
    producer:
      bootstrap-servers: localhost:9092
      key-serializer: org.apache.kafka.common.serialization.StringSerializer
      value-serializer: org.springframework.kafka.support.serializer.JsonSerializer
    consumer:
      bootstrap-servers: localhost:9092
      group-id: testGroup
      auto-offset-reset: earliest
      key-deserializer: org.apache.kafka.common.serialization.StringDeserializer
      value-deserializer: org.springframework.kafka.support.serializer.JsonDeserializer
      properties:
        spring.json.trusted.packages: '*'
```

# References :

https://www.youtube.com/watch?v=KQDTtvZMS9c&t=3263s