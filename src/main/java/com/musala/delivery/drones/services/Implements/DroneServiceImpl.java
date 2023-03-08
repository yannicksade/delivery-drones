package com.musala.delivery.drones.services.Implements;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.GenericPropertyMatcher;
import org.springframework.stereotype.Service;

import com.musala.delivery.drones.dto.DroneDto;
import com.musala.delivery.drones.dto.DroneRequestDto;
import com.musala.delivery.drones.entities.Drone;
import com.musala.delivery.drones.entities.Medication;
import com.musala.delivery.drones.enumerations.EStatus;
import com.musala.delivery.drones.exceptions.DroneAlreadyRegisteredException;
import com.musala.delivery.drones.exceptions.InvalidRequestException;
import com.musala.delivery.drones.exceptions.MedicationAlreadyRegisteredException;
import com.musala.delivery.drones.exceptions.ResourceNotFoundException;
import com.musala.delivery.drones.repositories.DroneRepository;
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
	public DroneDto registerDrone(DroneRequestDto request)
			throws InvalidRequestException, DroneAlreadyRegisteredException {
		ExampleMatcher exmMatcher = ExampleMatcher.matching();
		exmMatcher.withIgnorePaths("id").withMatcher("name", GenericPropertyMatcher.of(null));
		Example<Medication> example = Example.of(new Drone(request.getModel(), request.getSerialNumber()));
		if(droneRepository.exists(example)) throw new MedicationAlreadyRegisteredException();
		return toDroneDto(droneRepository.save(toDrone(request)));
	}

	private DroneDto toDroneDto(Drone drone) {
		return DroneDto.builder()
				.model(drone.getModel())
				.serialNumber(drone.getSerialNumber())
				.batteryCapacity(drone.getBatteryCapacity())
				.state(drone.getState())
				.weightLimit(drone.getWeightLimit()).build();
	}

	private Drone toDrone(DroneRequestDto droneRequest) {
		return Drone.builder()
		.model(dto.getModel())
		.serialNumber(dto.getSerialNumber())
		.batteryCapacity(dto.getBatteryCapacity())
		.state(dto.getState())
		.weightLimit(dto.getWeightLimit()).build();
	}

	@Override
	public DroneDto getDroneBySerialNumber(String serialNumber) throws ResourceNotFoundException {
		Optional<Drone> drone = droneRepository.findBySerialNumber(serialNumber);
		if(drone.isPresent()) return toDroneDto(drone.get());
		 throw new ResourceNotFoundException();
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
