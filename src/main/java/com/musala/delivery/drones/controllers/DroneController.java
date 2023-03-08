package com.musala.delivery.drones.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.musala.delivery.drones.entities.Drone;
import com.musala.delivery.drones.exceptions.DroneAlreadyRegisteredException;
import com.musala.delivery.drones.exceptions.InvalidRequestException;
import com.musala.delivery.drones.exceptions.ResourceNotFoundException;
import com.musala.delivery.drones.services.DroneDto;
import com.musala.delivery.drones.services.DroneRequestDto;
import com.musala.delivery.drones.services.DroneService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("api/v1/drone")
public class DroneController {
	
	private final DroneService droneService;
	
	@Autowired
	public DroneController(DroneService droneService) {
		this.droneService = droneService;
	}
	
	@GetMapping("details")
	private ResponseEntity<DroneDto> getDroneDetails(@RequestParam("serialNumber") String serialNumber) {
		return ResponseEntity.ok().body(droneService.getDroneBySerialNumber(serialNumber));
	}
	
	@GetMapping("all")
	private ResponseEntity<List<DroneDto>> getDrones() {
		return ResponseEntity.ok().body(droneService.getAllAvailableDrones());
	}
	
	@PostMapping("add")
    private ResponseEntity<DroneDto> addDrone(@Valid @RequestBody DroneRequestDto request) throws DroneAlreadyRegisteredException, InvalidRequestException {
        return ResponseEntity.ok().body(droneService.registerDrone(request));
    }
	
	@GetMapping("chechBattery")
	private ResponseEntity<DroneDto> getDroneBatteryLevel(@RequestParam("droneId") long id) throws ResourceNotFoundException {
		return ResponseEntity.ok().body(droneService.checkDroneBatteryLevelById(id));
	}
	
}