package com.musala.delivery.drones.exceptions;

@SuppressWarnings("serial")
public class DroneAlreadyRegisteredException extends BusinessException{

	public DroneAlreadyRegisteredException(String message) {
		super(message);
	}

}
