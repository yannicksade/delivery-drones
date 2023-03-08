package com.musala.delivery.drones.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MedicationDto {

	private String code;

	private String name;

	private float weight;

	private String image;

	private Long droneId;

	public String getCode() {
		return code;
	}

	public String getName() {
		return name;
	}

	public float getWeight() {
		return weight;
	}

	public String getImage() {
		return image;
	}

	public Long getDroneId() {
		return droneId;
	}
	
	
}
