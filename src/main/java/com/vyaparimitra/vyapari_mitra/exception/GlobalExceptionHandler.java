package com.vyaparimitra.vyapari_mitra.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    // Handle Resource Not Found Exception
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> handleResourceNotFoundException(
            ResourceNotFoundException ex, WebRequest request) {

        Map<String, Object> error = new HashMap<>();
        error.put("timestamp", LocalDateTime.now());
        error.put("message", ex.getMessage());
        error.put("status", HttpStatus.NOT_FOUND.value());
        error.put("error", "Not Found");

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    // Handle Unauthorized Exception
    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<?> handleUnauthorizedException(
            UnauthorizedException ex, WebRequest request) {

        Map<String, Object> error = new HashMap<>();
        error.put("timestamp", LocalDateTime.now());
        error.put("message", ex.getMessage());
        error.put("status", HttpStatus.UNAUTHORIZED.value());
        error.put("error", "Unauthorized");

        return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
    }

    // Handle File Storage Exception
    @ExceptionHandler(FileStorageException.class)
    public ResponseEntity<?> handleFileStorageException(
            FileStorageException ex, WebRequest request) {

        Map<String, Object> error = new HashMap<>();
        error.put("timestamp", LocalDateTime.now());
        error.put("message", ex.getMessage());
        error.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        error.put("error", "File Storage Error");

        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // Handle Validation Exception
    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<?> handleValidationException(
            ValidationException ex, WebRequest request) {

        Map<String, Object> error = new HashMap<>();
        error.put("timestamp", LocalDateTime.now());
        error.put("message", ex.getMessage());
        error.put("status", HttpStatus.BAD_REQUEST.value());
        error.put("error", "Validation Error");

        if (ex.getErrors() != null) {
            error.put("fieldErrors", ex.getErrors());
        }
        if (ex.getErrorList() != null) {
            error.put("errors", ex.getErrorList());
        }

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    // Handle Max Upload Size Exceeded Exception
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<?> handleMaxSizeException(
            MaxUploadSizeExceededException ex, WebRequest request) {

        Map<String, Object> error = new HashMap<>();
        error.put("timestamp", LocalDateTime.now());
        error.put("message", "फाइल साइझ १०MB पेक्षा कमी असावी!");
        error.put("status", HttpStatus.PAYLOAD_TOO_LARGE.value());
        error.put("error", "File Too Large");

        return new ResponseEntity<>(error, HttpStatus.PAYLOAD_TOO_LARGE);
    }

    // Handle Generic Exception
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGlobalException(
            Exception ex, WebRequest request) {

        Map<String, Object> error = new HashMap<>();
        error.put("timestamp", LocalDateTime.now());
        error.put("message", ex.getMessage());
        error.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        error.put("error", "Internal Server Error");

        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}