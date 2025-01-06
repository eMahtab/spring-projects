package net.mahtabalam.client;

import feign.Response;
import feign.codec.ErrorDecoder;
import net.mahtabalam.exception.FeignClientExceptionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.Map;

public class CustomErrorDecoder implements ErrorDecoder {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Exception decode(String methodKey, Response response) {
        if (response.status() == HttpStatus.NOT_FOUND.value()) {
            try {
                Map<String, Object> map = objectMapper.readValue(response.body().asInputStream(), Map.class);
                String errorMessage = map.get("error").toString();
                return new FeignClientExceptionResponse(ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body(Map.of("error", errorMessage)));
            } catch (IOException e) {
                return new FeignClientExceptionResponse(ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body(Map.of("error", "Resource not found")));
            }
        }
        return new Default().decode(methodKey, response);
    }
}
