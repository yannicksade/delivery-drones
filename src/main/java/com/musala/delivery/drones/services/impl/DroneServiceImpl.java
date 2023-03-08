package com.musala.delivery.drones.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.musala.delivery.drones.dto.DroneDto;
import com.musala.delivery.drones.dto.DroneRequestDto;
import com.musala.delivery.drones.dto.MedicationDto;
import com.musala.delivery.drones.dto.MedicationRequestDto;
import com.musala.delivery.drones.entities.Drone;
import com.musala.delivery.drones.enumerations.EStatus;
import com.musala.delivery.drones.exceptions.DroneAlreadyRegisteredException;
import com.musala.delivery.drones.exceptions.InvalidRequestException;
import com.musala.delivery.drones.exceptions.ResourceNotFoundException;
import com.musala.delivery.drones.mappers.DroneMapper;
import com.musala.delivery.drones.repositories.DroneRepository;
import com.musala.delivery.drones.services.DroneService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class DroneServiceImpl implements DroneService {

	private final DroneRepository droneRepository;

	private final DroneMapper droneMapper;

	@Autowired
	DroneServiceImpl(DroneRepository droneRepository, DroneMapper droneMapper) {
		this.droneRepository = droneRepository;
		this.droneMapper = droneMapper;
	}

	Optional<Drone> getDroneById(long id) {
		return droneRepository.findById(id);
	}

	@Override
	public List<DroneDto> getAllAvailableDrones() {
		return droneRepository.findByState(EStatus.IDLE).stream().map(droneMapper::toDto).collect(Collectors.toList());
	}

	@Override
	public DroneDto registerDrone(DroneRequestDto request)
			throws InvalidRequestException, DroneAlreadyRegisteredException {
		Optional<Drone> drone = droneRepository.findByModelAndSerialNumber(request.getModel(),
				request.getSerialNumber());
		if (drone.isPresent()) {
			throw new DroneAlreadyRegisteredException("the provided drone item is already registered");
		}
		DroneDto droneDto = validateDrone(request);
		log.info("A drone with  serial number {} and model {} is saving ", request.getSerialNumber(), request.getModel());
		return droneMapper.toDto(droneRepository.save(
				Drone.builder()
				.batteryCapacity(100)
				.state(droneDto.getState())
				.model(droneDto.getModel())
				.maxWeight(droneDto.getWeightLimit())
				.serialNumber(droneDto.getSerialNumber())
				));
	}

	@Override
	public DroneDto getDroneBySerialNumber(String serialNumber) throws ResourceNotFoundException {
		return droneMapper.toDto(droneRepository.findBySerialNumber(serialNumber).orElseThrow(
				() -> new ResourceNotFoundException("No drone found with the serial number: " + serialNumber)));

	}

	@Override
	public DroneDto validateDrone(DroneRequestDto request) throws InvalidRequestException {

		if (request.equals(null)) {
			throw new InvalidRequestException("Invalid data");
		} else if (request.getModel().name().isBlank()) {
			throw new InvalidRequestException("Drone Model is required");
		} else if (request.getSerialNumber().isEmpty()) {
			throw new InvalidRequestException("Drone serial number is required");
		} else if (request.getWeightLimit() == 0 || request.getWeightLimit() == 500) {
			throw new InvalidRequestException(
					"A drone total weight must be greater than 0 and lesser than 500 grammes");
		}
		return droneMapper.toDto(droneMapper.toEntity(request));

	}

	@Override
	public DroneDto updateDrone(DroneRequestDto request) {
		return droneMapper.toDto(droneRepository.save(droneMapper.toEntity(request)));
	}
	
	@Override
	public DroneDto checkDroneBatteryLevelById(long id) throws ResourceNotFoundException {
		return droneMapper.toDto(droneRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No drone found with the ID number" + id)));
	}

	@Override
	public void updateDroneStateById(long id, EStatus state) {
		log.info("A drone with ID {} is changed state to {}", id, state);
		droneRepository.updateDroneState(id, state);
	}

}
