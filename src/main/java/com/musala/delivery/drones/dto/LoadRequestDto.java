package com.musala.delivery.drones.dto;

import com.musala.delivery.drones.entities.Medication;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class LoadRequestDto extends HistoryRequestDto {
	private Long medicationId;
	private List<Long> medicationIds;
}
