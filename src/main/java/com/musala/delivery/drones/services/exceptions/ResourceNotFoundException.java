package com.musala.delivery.drones.services.exceptions;


@SuppressWarnings("serial")
public class ResourceNotFoundException extends BusinessException {

	public ResourceNotFoundException(String message) {
		super(message);
	}
}
