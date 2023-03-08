package com.musala.delivery.drones.services;

import java.util.List;

import com.musala.delivery.drones.enumerations.EStatus;
import com.musala.delivery.drones.exceptions.DroneAlreadyRegisteredException;
import com.musala.delivery.drones.exceptions.InvalidRequestException;
import com.musala.delivery.drones.exceptions.ResourceNotFoundException;

public interface DroneService {
	
	List<DroneDto> getAllAvailableDrones();
	
	DroneDto registerDrone(DroneRequestDto droneRequest) throws InvalidRequestException, DroneAlreadyRegisteredException;
	
	DroneDto getDroneBySerialNumber(String serialNumber) throws ResourceNotFoundException;
	
	DroneDto validateDrone(DroneRequestDto request) throws InvalidRequestException;
	
	DroneDto checkDroneBatteryLevelById(long id) throws ResourceNotFoundException;
	
	void updateDroneStateById(long id, EStatus state);
}
