package com.musala.delivery.drones.mappers;

import org.mapstruct.Mapper;
import org.springframework.stereotype.Service;

import com.musala.delivery.drones.dto.MedicationDto;
import com.musala.delivery.drones.dto.MedicationRequestDto;
import com.musala.delivery.drones.entities.Medication;

@Mapper(componentModel = "spring")
@Service
public abstract class MedicationMapper {
	
	public abstract Medication toEntity(MedicationRequestDto medicationDto);
	
	public abstract MedicationDto toDto(Medication medication);
}
