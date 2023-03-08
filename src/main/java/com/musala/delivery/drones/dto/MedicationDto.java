package com.musala.delivery.drones.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MedicationDto {

	private String code;

	private String name;

	private Float weight;

	private String image;

	private Long droneId;
}
