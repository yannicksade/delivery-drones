package com.musala.delivery.drones.services;

import java.util.List;
import com.musala.delivery.drones.dto.MedicationDto;
import com.musala.delivery.drones.dto.MedicationRequestDto;
import com.musala.delivery.drones.services.exceptions.InvalidRequestException;
import com.musala.delivery.drones.services.exceptions.MedicationAlreadyRegisteredException;
import com.musala.delivery.drones.services.exceptions.ResourceNotFoundException;

public interface MedicationService {
	
	List<MedicationDto> getAllMedicationsByDrone(long droneId);
	
	MedicationDto createMedication(MedicationRequestDto request) throws InvalidRequestException, MedicationAlreadyRegisteredException;
	
	MedicationDto getDroneByCode(String code) throws ResourceNotFoundException;
	
	MedicationDto validateMedication(MedicationRequestDto request) throws InvalidRequestException;
	
	MedicationDto updateMedication(MedicationRequestDto request);
}
