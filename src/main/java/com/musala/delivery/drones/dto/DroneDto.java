package com.musala.delivery.drones.dto;

import com.musala.delivery.drones.enumerations.EModel;
import com.musala.delivery.drones.enumerations.EStatus;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DroneDto {

	private EModel model;
	
	private String serialNumber;

	private Integer batteryCapacity;

	private EStatus state;

	private Float weightLimit;
}
