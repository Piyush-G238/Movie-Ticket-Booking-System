package com.app.audienceize.exceptionhandlers;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.DateTimeException;
import java.time.format.DateTimeParseException;
import java.util.*;

@RestControllerAdvice
public class MovieRestControllerExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException exception) {
        List<FieldError> fieldErrors = exception.getFieldErrors();
        Map<String, String> map = new HashMap<>();
        fieldErrors.forEach(err -> map.put(err.getField(), err.getDefaultMessage()));
        map.put("status", "400 Bad request");
        map.put("timestamp", new Date().toString());
        return new ResponseEntity<>(map, HttpStatusCode.valueOf(400));
    }

    @ExceptionHandler(DateTimeException.class)
    public ResponseEntity<Map<String, String>> dateTimeExceptionHandler(DateTimeException exception) {
        Map<String, String> map = new HashMap<>();
        map.put("message", exception.getMessage());
        map.put("status", "500 internal server error");
        map.put("timestamp", new Date().toString());
        return new ResponseEntity<>(map, HttpStatusCode.valueOf(500));
    }

    @ExceptionHandler(DateTimeParseException.class)
    public ResponseEntity<Map<String, String>> dateTimeParseExceptionHandler(DateTimeParseException exception) {
        Map<String, String> map = new HashMap<>();
        map.put("message", "Date should be in the format (yyyy-mm-dd)");
        map.put("status", "500 internal server error");
        map.put("timestamp", new Date().toString());
        return new ResponseEntity<>(map, HttpStatusCode.valueOf(500));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, String>> httpMessageNotReadableExceptionHandler(HttpMessageNotReadableException exception) {
        Map<String, String> map = new HashMap<>();
        map.put("message", exception.getMessage());
        map.put("status", "400 Bad request");
        map.put("timestamp", new Date().toString());
        return new ResponseEntity<>(map, HttpStatusCode.valueOf(400));
    }
    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<Map<String, String>> noSuchElementExceptionHandler(NoSuchElementException exception) {
        Map<String, String> map = new HashMap<>();
        map.put("message", "This movie is currently not present");
        map.put("status", "404 Not found");
        map.put("timestamp", new Date().toString());
        return new ResponseEntity<>(map, HttpStatusCode.valueOf(404));
    }
}
