# Streaming API

To send a stream of data, **server sets the Transfer-Encoding to chunked**

## Streaming endpoint : /stream1

!["chunked data"](images/stream1.png?raw=true)

## Streaming endpoint : /stream2

!["chunked data"](images/stream2.png?raw=true)

```java
@RestController
public class StreamController {

    @GetMapping(value = "/stream1", produces = "text/plain")
    public void streamData(HttpServletResponse response) {
        // Setting content type and encoding
        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");

        // Explicitly setting headers
        response.setHeader("Transfer-Encoding", "chunked");
        response.setHeader("Connection", "keep-alive");

        try (PrintWriter writer = response.getWriter()) {
            for (int i = 0; i < 10; i++) {
                writer.write("Chunk " + (i + 1) + " - Time: " + LocalTime.now() + "\n");
                writer.flush();  // Flush the buffer to send data immediately
                Thread.sleep(1000);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @GetMapping(value = "/stream2", produces = "text/event-stream")
    public void streamEventData(HttpServletResponse response) {
        response.setContentType("text/event-stream");
        response.setCharacterEncoding("UTF-8");

        try (PrintWriter writer = response.getWriter()) {
            for (int i = 0; i < 10; i++) {  // Stream data 10 times (for demo)
                writer.write("data: Chunk " + (i + 1) + " - Time: " + LocalTime.now() + "\n");
                writer.flush();  // Flush the buffer to send data immediately
                Thread.sleep(1000);  // Simulate delay (1 second)
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
```
