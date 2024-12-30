# Producing & Consuming String messages with Spring Kafka

This demo project is based on https://www.youtube.com/watch?v=KQDTtvZMS9c&t=1988s , in which a producer sends messages to test-topic and a consumer consumes those messages. The messages are plain text (String) messages. Make sure Kafka server is running on default port 9092, before running the application.

### Project

!["Spring Kafka Project"](images/project.png?raw=true)

## Messages API
```java
@RestController
@RequestMapping("/api/v1/messages")
public class MessageController {

    @Autowired
    private KafkaStringProducer kafkaProducer;

    @PostMapping
    public ResponseEntity<String> sendMessage(@RequestBody String message) {
        kafkaProducer.sendMessage(message);
        return ResponseEntity.ok("Message queued successfully");
    }
}
```

!["Messages API"](images/messages-api.png?raw=true)

### Producer
```java
@Service
public class KafkaStringProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private static final Logger logger = LoggerFactory.getLogger(KafkaStringProducer.class);

    public KafkaStringProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }
    public void sendMessage(String message) {
        logger.info("Sending message= {}",message);
        kafkaTemplate.send("test-topic", message);
    }
}
```

### Consumer
```java
@Service
public class KafkaStringConsumer {

    private static final Logger logger = LoggerFactory.getLogger(KafkaStringConsumer.class);

    @KafkaListener(topics = "test-topic", groupId = "testGroup")
    public void consumeMessage(String message) {
        logger.info("Consuming the message from test-topic={}", message);
    }
}
```
### application.yml
```yml
spring:
  application:
    name: spring-kafka
  kafka:
    consumer:
      bootstrap-servers: localhost:9092
      group-id: testGroup
      auto-offset-reset: earliest
      key-deserializer: org.apache.kafka.common.serialization.StringDeserializer
      value-deserializer: org.apache.kafka.common.serialization.StringDeserializer
      properties:
        spring.json.trusted.packages: '*'
    producer:
      bootstrap-servers: localhost:9092
      key-serializer: org.apache.kafka.common.serialization.StringSerializer
      value-serializer: org.apache.kafka.common.serialization.StringSerializer
```


# References :
https://github.com/ali-bouali/apache-kafka-with-spring-boot-reactive
