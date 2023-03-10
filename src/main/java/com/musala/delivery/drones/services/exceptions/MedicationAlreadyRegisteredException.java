package com.musala.delivery.drones.services.exceptions;

@SuppressWarnings("serial")
public class MedicationAlreadyRegisteredException extends BusinessException{

	public MedicationAlreadyRegisteredException(String message) {
		super(message);
	}

}
