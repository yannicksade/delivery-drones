package com.musala.delivery.drones.services.Implements;

import java.util.List;

import com.musala.delivery.drones.enumerations.EStatus;
import com.musala.delivery.drones.exceptions.DroneAlreadyRegisteredException;
import com.musala.delivery.drones.exceptions.InvalidRequestException;
import com.musala.delivery.drones.exceptions.ResourceNotFoundException;
import com.musala.delivery.drones.services.DroneDto;
import com.musala.delivery.drones.services.DroneRequestDto;
import com.musala.delivery.drones.services.DroneService;

public class DroneServiceImpl implements DroneService{

	@Override
	public List<DroneDto> getAllAvailableDrones() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DroneDto registerDrone(DroneRequestDto droneRequest)
			throws InvalidRequestException, DroneAlreadyRegisteredException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DroneDto getDroneBySerialNumber(String serialNumber) throws ResourceNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DroneDto validateDrone(DroneRequestDto request) throws InvalidRequestException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DroneDto checkDroneBatteryLevelById(long id) throws ResourceNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateDroneStateById(long id, EStatus state) {
		// TODO Auto-generated method stub
		
	}

}
