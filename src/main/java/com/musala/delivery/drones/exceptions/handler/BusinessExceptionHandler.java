package com.musala.delivery.drones.exceptions.handler;

import com.musala.delivery.drones.entities.dto.ErrorMessage;

import java.time.LocalDateTime;

import com.musala.delivery.drones.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class BusinessExceptionHandler {

    @ExceptionHandler(InvalidRequestException.class)
    public ResponseEntity<ErrorMessage> handleInvalidDataException(InvalidRequestException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                ErrorMessage.builder()
                        .errorCode(HttpStatus.BAD_REQUEST)
                        .date(LocalDateTime.now())
                        .message("Invalid request data")
                        .description(ex.getMessage())
                        .build()
        );
    }

    @ExceptionHandler(DroneAlreadyRegisteredException.class)
    public ResponseEntity<ErrorMessage> droneAlreadyRegisteredException(DroneAlreadyRegisteredException ex) {
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(
                ErrorMessage.builder()
                        .errorCode(HttpStatus.NOT_ACCEPTABLE)
                        .date(LocalDateTime.now())
                        .message("Similar Drone exists already")
                        .description(ex.getMessage())
                        .build()
        );
    }

    @ExceptionHandler(MedicationAlreadyRegisteredException.class)
    public ResponseEntity<ErrorMessage> medicationAlreadyRegisteredException(MedicationAlreadyRegisteredException ex) {
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(
                ErrorMessage.builder()
                        .errorCode(HttpStatus.NOT_ACCEPTABLE)
                        .date(LocalDateTime.now())
                        .message("Similar Medication exists")
                        .description(ex.getMessage())
                        .build()
        );
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorMessage> resourceNotFound(ResourceNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                ErrorMessage.builder()
                        .errorCode(HttpStatus.NOT_FOUND)
                        .date(LocalDateTime.now())
                        .message("No data resource found")
                        .description(ex.getMessage())
                        .build()
        );
    }

    @ExceptionHandler(LowBatteryException.class)
    public ResponseEntity<ErrorMessage> lowBatteryException(LowBatteryException ex) {
        return ResponseEntity.status(HttpStatus.BANDWIDTH_LIMIT_EXCEEDED).body(
                ErrorMessage.builder()
                        .errorCode(HttpStatus.BANDWIDTH_LIMIT_EXCEEDED)
                        .date(LocalDateTime.now())
                        .message("Drone Battery is low")
                        .description(ex.getMessage())
                        .build()
        );
    }

    @ExceptionHandler(DroneOverloadException.class)
    public ResponseEntity<ErrorMessage> droneOverloadException(DroneOverloadException ex) {
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(
                ErrorMessage.builder()
                        .errorCode(HttpStatus.NOT_ACCEPTABLE)
                        .date(LocalDateTime.now())
                        .message("Drone capacity exceeded")
                        .description(ex.getMessage())
                        .build()
        );
    }

    @ExceptionHandler(DroneAlreadyBusyException.class)
    public ResponseEntity<ErrorMessage> droneBusyExceptionException(DroneAlreadyBusyException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                ErrorMessage.builder()
                        .errorCode(HttpStatus.BAD_REQUEST)
                        .date(LocalDateTime.now())
                        .message("Drone Busy")
                        .description(ex.getMessage())
                        .build()
        );
    }

    @ExceptionHandler(MedicationLoadedAlreadyException.class)
    public ResponseEntity<ErrorMessage> loadedAlready(MedicationLoadedAlreadyException ex) {
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    ErrorMessage.builder()
                            .errorCode(HttpStatus.BAD_REQUEST)
                            .date(LocalDateTime.now())
                            .message("Medication Loaded Already")
                            .description(ex.getMessage())
                            .build()
            );
        }
    }
}


