package com.musala.delivery.drones.entities.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MedicationDto {

	private String code;

	private String name;

	private Double weight;

	private String image;
	
}
