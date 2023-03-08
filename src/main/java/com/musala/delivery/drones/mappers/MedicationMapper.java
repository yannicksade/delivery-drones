package com.musala.delivery.drones.mappers;

import com.musala.delivery.drones.dto.MedicationDto;
import com.musala.delivery.drones.entities.Medication;

public abstract class MedicationMapper {
	
	abstract Medication toEntity(MedicationDto medicationDto);
	
	abstract MedicationDto toDto(Medication medication);
}
