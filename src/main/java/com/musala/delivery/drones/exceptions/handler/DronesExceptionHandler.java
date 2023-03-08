package com.musala.delivery.drones.exceptions.handler;

import com.musala.delivery.drones.exceptions.InvalidDataException;
import com.musala.delivery.drones.exceptions.ResourceNotFoundException;
import com.musala.delivery.drones.services.MedicationRequestDto;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class DronesExceptionHandler {

    @ExceptionHandler(InvalidDataException.class)
    public MedicationRequestDto handleInvalidDataException(Model model, InvalidDataException invalidDataException) {
        return null;
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public MedicationRequestDto handleInvalidDataException(Model model, ResourceNotFoundException resourceNotFoundException) {
        return null;
    }
}
