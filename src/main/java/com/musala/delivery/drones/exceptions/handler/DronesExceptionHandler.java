package com.musala.delivery.drones.exceptions.handler;

import com.musala.delivery.drones.exceptions.InvalidDataException;
import com.musala.delivery.drones.exceptions.ResourceNotFoundException;
import com.musala.delivery.drones.services.ResponseDto;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class DronesExceptionHandler {

    @ExceptionHandler(InvalidDataException.class)
    public ResponseDto handleInvalidDataException(Model model, InvalidDataException invalidDataException) {
        return null;
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseDto handleInvalidDataException(Model model, ResourceNotFoundException resourceNotFoundException) {
        return null;
    }
}
