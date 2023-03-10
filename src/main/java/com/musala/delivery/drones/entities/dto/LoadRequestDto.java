package com.musala.delivery.drones.entities.dto;

import lombok.Data;

import java.util.List;

@Data
public class LoadRequestDto extends HistoryRequestDto {
	private Long medicationId;
	private List<Long> medicationIds;
	LoadRequestDto() {
		super();
	}
}
