package com.musala.delivery.drones.entities.dto;

import com.musala.delivery.drones.enumerations.EModel;

import lombok.*;

@Data
@Builder
public class DroneRequestDto extends LoadRequest {
	private String serialNumber;

	private EModel model;

	private float weightLimit;

}
