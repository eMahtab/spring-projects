package net.mahtabalam.exception;

import org.springframework.http.ResponseEntity;

public class FeignClientExceptionResponse extends RuntimeException {
    private final ResponseEntity<Object> response;

    public FeignClientExceptionResponse(ResponseEntity<Object> response) {
        this.response = response;
    }

    public ResponseEntity<Object> getResponse() {
        return response;
    }
}