package com.hcg.simpletaskmanager.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(TaskException.class)
    public ResponseEntity<GlobalErrorResponse> handleTaskException(Exception ex, WebRequest request) {
        GlobalErrorResponse errors = new GlobalErrorResponse();
        errors.setTimestamp(LocalDateTime.now());
        errors.setErrorMessage(ex.getMessage());
        if (ex.getMessage().contains(TaskException.NOT_FOUND)) {
            errors.setStatus(HttpStatus.NOT_FOUND.value());
            return new ResponseEntity<>(errors, HttpStatus.NOT_FOUND);
        } else {
            errors.setStatus(HttpStatus.BAD_REQUEST.value());
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }
    }

}
