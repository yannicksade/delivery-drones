package com.musala.delivery.drones.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MedicationRequestDto {
	private String code; //auto generate

	private String name;

	private float weight;

	private String image;

	private Long droneId;

	public String getName() {
		return name;
	}
	public long getDroneId() {
		return droneId;
	}
	public String getCode() {
		return code;
	}
	public float getWeight() {
		return weight;
	}
	public String getImage() {
		return image;
	}
	
	
}
