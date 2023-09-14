package com.echonrich.hr.global.exception;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@RestControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(CustomLogicException.class)
    public ResponseEntity<ExceptionResponse> handleBusinessLogicException(CustomLogicException e) {
        final ExceptionResponse exceptionResponse = ExceptionResponse.builder()
                .title(e.getExceptionCode().getStatus().getReasonPhrase())
                .status(e.getExceptionCode().getStatus().value())
                .detail(e.getMessage())
                .timestamp(LocalDateTime.now())
                .build();
        return ResponseEntity.status(exceptionResponse.getStatus()).body(exceptionResponse);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ExceptionResponse> handleConstraintViolationException(ConstraintViolationException e) {
        final ExceptionResponse exceptionResponse = ExceptionResponse.builder()
                .title(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .status(HttpStatus.BAD_REQUEST.value())
                .detail(e.getMessage())
                .timestamp(LocalDateTime.now())
                .build();
        return ResponseEntity.status(exceptionResponse.getStatus()).body(exceptionResponse);
    }
}
