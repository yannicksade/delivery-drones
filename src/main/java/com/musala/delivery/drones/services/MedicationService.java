package com.musala.delivery.drones.services;

import java.util.List;

import com.musala.delivery.drones.entities.Medication;
import com.musala.delivery.drones.entities.dto.DroneRequestDto;
import com.musala.delivery.drones.entities.dto.MedicationDto;
import com.musala.delivery.drones.entities.dto.MedicationRequestDto;
import com.musala.delivery.drones.exceptions.BusinessErrorException;
import com.musala.delivery.drones.exceptions.InvalidRequestException;
import com.musala.delivery.drones.exceptions.MedicationAlreadyRegisteredException;
import com.musala.delivery.drones.exceptions.ResourceNotFoundException;
import org.springframework.web.multipart.MultipartFile;

public interface MedicationService {
	
	List<MedicationDto> getAllMedicationsByDrone(String droneId);
	
	MedicationDto createMedication(MedicationRequestDto request) throws InvalidRequestException, MedicationAlreadyRegisteredException, BusinessErrorException;
	
	MedicationDto getMedicationByCode(String code) throws ResourceNotFoundException;
	
	MedicationDto validateMedication(MedicationRequestDto request) throws InvalidRequestException;
	
	MedicationDto updateMedication(MedicationRequestDto request) throws BusinessErrorException;

	void removeMedication(String code) throws BusinessErrorException;

    MedicationDto updatedImage(String code, MultipartFile multiPartFile) throws BusinessErrorException;

	boolean checkIfMedicationIsLoadedOrDelivered(String medicationCode);

    Medication findByCode(String code);

    List<MedicationDto> listAllMedications(MedicationRequestDto request);
}
