package com.musala.delivery.drones.entities.dto;

import com.musala.delivery.drones.entities.ActivityHistory;
import com.musala.delivery.drones.entities.Medication;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoadRequest {
	private String originLocation;
	private String destinationLocation;
	private Medication medication;
	private List<Medication> medications;
}
