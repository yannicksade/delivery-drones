package com.musala.delivery.drones.exceptions.handler;

import com.musala.delivery.drones.entities.dto.ErrorMessage;
import com.musala.delivery.drones.exceptions.BusinessErrorException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class NonBusinessExceptionHandler {

    @ExceptionHandler(BusinessErrorException.class)
    public ResponseEntity<ErrorMessage> integrityViolation(BusinessErrorException ex) {
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    ErrorMessage.builder()
                            .errorCode(HttpStatus.BAD_REQUEST)
                            .date(LocalDateTime.now())
                            .message("Bad request data")
                            .description(ex.getMessage().split(":")[0])
                            .build()
            );
        }
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorMessage> methodArgumentNotValid(HttpMessageNotReadableException ex) {
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    ErrorMessage.builder()
                            .errorCode(HttpStatus.BAD_REQUEST)
                            .date(LocalDateTime.now())
                            .message("Bad request data")
                            .description(ex.getMessage().split(":")[0])
                            .build()
            );
        }
    }
}
