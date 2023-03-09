package com.musala.delivery.drones.exceptions.handler;

import com.musala.delivery.drones.dto.ErrorMessage;
import com.musala.delivery.drones.exceptions.DroneAlreadyRegisteredException;
import com.musala.delivery.drones.exceptions.DroneOverloadException;
import com.musala.delivery.drones.exceptions.InvalidRequestException;
import com.musala.delivery.drones.exceptions.LowBatteryException;
import com.musala.delivery.drones.exceptions.MedicationAlreadyRegisteredException;
import com.musala.delivery.drones.exceptions.ResourceNotFoundException;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class DronesExceptionHandler {

    @ExceptionHandler(InvalidRequestException.class)
    public ResponseEntity<ErrorMessage> handleInvalidDataException(InvalidRequestException ex) {
    	return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
    			ErrorMessage.builder()
    				.errorCode(HttpStatus.BAD_REQUEST)
    				.date(LocalDateTime.now())
    				.description("Invalid request data")
    				.message(ex.getMessage())
						.build()
    			);
    }

    @ExceptionHandler(DroneAlreadyRegisteredException.class)
    public ResponseEntity<ErrorMessage> droneAlreadyRegisteredException(DroneAlreadyRegisteredException ex) {
    	return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(
    			ErrorMessage.builder()
    				.errorCode(HttpStatus.ALREADY_REPORTED)
    				.date(LocalDateTime.now())
    				.description("Similar Drone exists already")
    				.message(ex.getMessage())
						.build()
    			);
    }
    @ExceptionHandler(MedicationAlreadyRegisteredException.class)
    public ResponseEntity<ErrorMessage> medicationAlreadyRegisteredException(MedicationAlreadyRegisteredException ex) {
    	return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(
    			ErrorMessage.builder()
    				.errorCode(HttpStatus.ALREADY_REPORTED)
    				.date(LocalDateTime.now())
    				.description("Similar Medication exists already")
    				.message(ex.getMessage())
						.build()
    			);
    }
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorMessage> resourceNotFound(ResourceNotFoundException ex) {
    	return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
    			ErrorMessage.builder()
    				.errorCode(HttpStatus.NOT_FOUND)
    				.date(LocalDateTime.now())
    				.description("No Data found")
    				.message(ex.getMessage())
						.build()
    			);
    }
    @ExceptionHandler(LowBatteryException.class)
    public ResponseEntity<ErrorMessage> lowBatteryException(LowBatteryException ex) {
    	return ResponseEntity.status(HttpStatus.BANDWIDTH_LIMIT_EXCEEDED).body(
    			ErrorMessage.builder()
    				.errorCode(HttpStatus.BANDWIDTH_LIMIT_EXCEEDED)
    				.date(LocalDateTime.now())
    				.description("Low Battery")
    				.message(ex.getMessage())
						.build()
    			);
    }
    @ExceptionHandler(DroneOverloadException.class)
    public ResponseEntity<ErrorMessage> droneOverloadException(DroneOverloadException ex) {
    	return ResponseEntity.status(HttpStatus.BANDWIDTH_LIMIT_EXCEEDED).body(
    			ErrorMessage.builder()
    				.errorCode(HttpStatus.BANDWIDTH_LIMIT_EXCEEDED)
    				.date(LocalDateTime.now())
    				.description("Drone Capacity exceeded")
    				.message(ex.getMessage())
						.build()
    			);
    }
    
}
