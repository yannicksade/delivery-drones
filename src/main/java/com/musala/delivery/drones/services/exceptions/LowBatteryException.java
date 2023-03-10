package com.musala.delivery.drones.services.exceptions;

@SuppressWarnings("serial")
public class LowBatteryException extends BusinessException{

	public LowBatteryException(String message) {
		super(message);
	}

}
