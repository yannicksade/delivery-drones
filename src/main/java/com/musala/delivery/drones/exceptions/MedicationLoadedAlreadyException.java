package com.musala.delivery.drones.exceptions;

import com.musala.delivery.drones.exceptions.BusinessException;

public class MedicationLoadedAlreadyException extends BusinessException {
    public MedicationLoadedAlreadyException(String message) {
        super(message);
    }
}
