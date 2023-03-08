package com.musala.delivery.drones.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.musala.delivery.drones.dto.DroneDto;
import com.musala.delivery.drones.dto.DroneRequestDto;
import com.musala.delivery.drones.dto.MedicationDto;
import com.musala.delivery.drones.dto.MedicationRequestDto;
import com.musala.delivery.drones.exceptions.InvalidRequestException;
import com.musala.delivery.drones.exceptions.MedicationAlreadyRegisteredException;
import com.musala.delivery.drones.exceptions.ResourceNotFoundException;
import com.musala.delivery.drones.services.MedicationService;

import jakarta.validation.Valid;


@RestController
@RequestMapping("medication")
//@ApiOperation("Medication API")
public class MedicationController {
	
	private final MedicationService medicationService;
	
	@Autowired
	private MedicationController(MedicationService medicationService) {
		this.medicationService = medicationService;
	}
	
	@GetMapping("details")
	private ResponseEntity<MedicationDto> getMedicationDetails(@RequestParam("code") String code) throws ResourceNotFoundException {
		return ResponseEntity.ok().body(medicationService.getDroneByCode(code));
	}
	
	@GetMapping("allMedicationsByDrone")
	private ResponseEntity<List<MedicationDto>> getDroneMedications(@RequestParam("droneRequestDto") MedicationRequestDto request) {
		return ResponseEntity.ok().body(medicationService.getAllMedicationsByDrone(request.getDroneId()));
	}
	
	@PostMapping("create")
    public ResponseEntity<MedicationDto> createMedication(@Valid @RequestBody MedicationRequestDto request) throws InvalidRequestException, MedicationAlreadyRegisteredException {
        return ResponseEntity.ok().body(medicationService.createMedication(request));
    }
	
	@PutMapping("update")
	private ResponseEntity<MedicationDto> update(@Valid @RequestBody MedicationRequestDto request) {
		return ResponseEntity.ok().body(medicationService.updateOrloadMedication(request));
	}
}


