package com.musala.delivery.drones.entities.dto;

import com.musala.delivery.drones.enumerations.EModel;
import com.musala.delivery.drones.enumerations.EStatus;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DroneDto {
	private String serialNumber;

	private EModel model;

	private Integer batteryLevel;

	private EStatus state;

	private Double weightLimit;
}
