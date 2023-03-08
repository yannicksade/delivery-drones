package com.musala.delivery.drones.services.Implements;

import java.util.List;import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.musala.delivery.drones.entities.Drone;
import com.musala.delivery.drones.enumerations.EStatus;
import com.musala.delivery.drones.exceptions.DroneAlreadyRegisteredException;
import com.musala.delivery.drones.exceptions.InvalidRequestException;
import com.musala.delivery.drones.exceptions.ResourceNotFoundException;
import com.musala.delivery.drones.repositories.DroneRepository;
import com.musala.delivery.drones.services.DroneDto;
import com.musala.delivery.drones.services.DroneRequestDto;
import com.musala.delivery.drones.services.DroneService;

@Service
public class DroneServiceImpl implements DroneService {

	private final DroneRepository droneRepository;
	
	@Autowired
	DroneServiceImpl(DroneRepository droneRepository) {
		this.droneRepository = droneRepository;
	}
	@Override
	public List<DroneDto> getAllAvailableDrones() {
		return droneRepository.findAll().stream().map(this::toDroneDto).collect(Collectors.toList());
	}

	@Override
	public DroneDto registerDrone(DroneRequestDto droneRequest)
			throws InvalidRequestException, DroneAlreadyRegisteredException {
		Drone drone = droneRepository.save(toDrone(droneRequest));
		return toDroneDto(drone);
	}

	private DroneDto toDroneDto(Drone drone) {
		DroneDto dto = new DroneDto();
		//set
		return dto;
	}
	private Drone toDrone(DroneRequestDto droneRequest) {
		Drone drone = new Drone();
		//set
		return drone;
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
