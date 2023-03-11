package com.musala.delivery.drones.exceptions;

@SuppressWarnings("serial")
public class MedicationAlreadyRegisteredException extends BusinessException{

	public MedicationAlreadyRegisteredException(String message) {
		super(message);
	}

}
