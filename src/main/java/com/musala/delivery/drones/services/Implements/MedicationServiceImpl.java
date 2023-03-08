package com.musala.delivery.drones.services.Implements;

import java.util.List;

import com.musala.delivery.drones.exceptions.InvalidRequestException;
import com.musala.delivery.drones.exceptions.MedicationAlreadyRegisteredException;
import com.musala.delivery.drones.exceptions.ResourceNotFoundException;
import com.musala.delivery.drones.services.MedicationDto;
import com.musala.delivery.drones.services.MedicationRequestDto;
import com.musala.delivery.drones.services.MedicationService;

public class MedicationServiceImpl implements MedicationService {

	@Override
	public List<MedicationDto> getAllMedicationsByDrone(long droneId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MedicationDto createMedication(MedicationRequestDto request)
			throws InvalidRequestException, MedicationAlreadyRegisteredException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MedicationDto getDroneByCode(String code) throws ResourceNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MedicationDto validateMedication(MedicationRequestDto request)
			throws InvalidRequestException, ResourceNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

}
