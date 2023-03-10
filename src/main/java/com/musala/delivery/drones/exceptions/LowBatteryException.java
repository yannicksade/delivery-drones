package com.musala.delivery.drones.exceptions;

@SuppressWarnings("serial")
public class LowBatteryException extends BusinessException{

	public LowBatteryException(String message) {
		super(message);
	}

}
