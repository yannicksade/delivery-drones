package com.musala.delivery.drones.dto;

import com.musala.delivery.drones.enumerations.EModel;

import lombok.*;

@Data
@Builder
public class DroneRequestDto extends LoadRequest {
	private EModel model;

	private String serialNumber;

	private float weightLimit;

}
