package com.design.poc.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DuplicateEmailException.class)
    public ResponseEntity<Map<String, Object>> handleDuplicateEmailException(DuplicateEmailException e) {
        Map<String, Object> errorDetail = new HashMap<>();
        errorDetail.put("timeStamp", LocalDateTime.now());
        errorDetail.put("status", HttpStatus.BAD_REQUEST.value());
        errorDetail.put("error", "Bad Request");
        errorDetail.put("message", e.getMessage());
        return new ResponseEntity<>(errorDetail, HttpStatus.BAD_REQUEST);
    }
}
