package com.musala.delivery.drones.dto;

import com.musala.delivery.drones.enumerations.EModel;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DroneRequestDto {
	private EModel model;

	private String serialNumber;

	private float weightLimit;

	public EModel getModel() {
		return model;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public float getWeightLimit() {
		return weightLimit;
	}
	
	
}
