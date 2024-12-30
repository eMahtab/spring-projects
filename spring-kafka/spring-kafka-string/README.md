# Producing & Consuming String messages with Spring Kafka

## Message API
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
