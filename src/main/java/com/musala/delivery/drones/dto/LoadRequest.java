package com.musala.delivery.drones.dto;

import com.musala.delivery.drones.entities.Medication;
import lombok.Data;

import java.util.List;

@Data
public class LoadRequest {
	private Medication medication;
	private List<Medication> medications;
}
