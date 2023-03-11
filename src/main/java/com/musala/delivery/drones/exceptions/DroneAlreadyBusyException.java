package com.musala.delivery.drones.exceptions;

public class DroneAlreadyBusyException extends BusinessException{
    public DroneAlreadyBusyException(String message) {
        super(message);
    }
}
