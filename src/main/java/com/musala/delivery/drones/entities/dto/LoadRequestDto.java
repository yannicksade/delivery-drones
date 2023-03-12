package com.musala.delivery.drones.entities.dto;

import lombok.*;

import java.util.List;

@Data
@Builder
public class LoadRequestDto {
	private String originLocation;
	private String destinationLocation;
	private List<String> medicationCodeList;
}
