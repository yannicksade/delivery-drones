package com.musala.delivery.drones.services.exceptions;


import java.util.function.Supplier;

@SuppressWarnings("serial")
public class ResourceNotFoundException extends BusinessException {

	public ResourceNotFoundException(String message) {
		super(message);
	}
}
