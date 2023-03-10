package com.musala.delivery.drones.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.musala.delivery.drones.dto.DroneDto;
import com.musala.delivery.drones.dto.DroneRequestDto;
import com.musala.delivery.drones.services.exceptions.DroneAlreadyRegisteredException;
import com.musala.delivery.drones.services.exceptions.InvalidRequestException;
import com.musala.delivery.drones.services.exceptions.ResourceNotFoundException;
import com.musala.delivery.drones.services.DroneService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("drone")
public class DroneController {

	private final DroneService droneService;

	@Autowired
	public DroneController(DroneService droneService) {
		this.droneService = droneService;
	}

	@GetMapping("details")
	private ResponseEntity<DroneDto> getDroneDetails(@RequestParam("serialNumber") String serialNumber)
			throws ResourceNotFoundException {
		return ResponseEntity.ok().body(droneService.getDroneBySerialNumber(serialNumber));
	}

	@GetMapping("allAvailable")
	private ResponseEntity<List<DroneDto>> getDrones() {
		return ResponseEntity.ok().body(droneService.getAllAvailableDrones());
	}

	@PostMapping("add")
	private ResponseEntity<DroneDto> addDrone(@Valid @RequestBody DroneRequestDto request)
			throws DroneAlreadyRegisteredException, InvalidRequestException {
		return ResponseEntity.ok().body(droneService.registerDrone(request));
	}

	@GetMapping("checkBattery")
	private ResponseEntity<Float> getDroneBatteryLevel(@RequestParam("droneId") long id)
			throws ResourceNotFoundException {
		return ResponseEntity.ok().body(droneService.checkDroneBatteryLevelById(id));
	}

	@PutMapping("update")
	private ResponseEntity<DroneDto> update(@Valid @RequestBody DroneRequestDto request) {
		return ResponseEntity.ok().body(droneService.updateDrone(request));
	}
}