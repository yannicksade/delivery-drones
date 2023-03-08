package com.musala.delivery.drones.services;

import java.io.Serializable;

import com.musala.delivery.drones.enumerations.EModel;
import com.musala.delivery.drones.enumerations.EStatus;

import lombok.Data;

@Data
public class DroneDto implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private EModel model;
	
	private String serialNumber;

	private Integer batteryCapacity;

	private EStatus state;

	private Float weightLimit;
}
