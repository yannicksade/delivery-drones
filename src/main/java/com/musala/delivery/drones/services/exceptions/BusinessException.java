package com.musala.delivery.drones.services.exceptions;

@SuppressWarnings("serial")
public class BusinessException extends RuntimeException {
	
	public BusinessException(String message) {
		super(message);
	}

}
