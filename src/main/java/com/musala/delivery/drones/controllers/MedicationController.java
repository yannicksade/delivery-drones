package com.musala.delivery.drones.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.musala.delivery.drones.services.MedicationDto;
import com.musala.delivery.drones.services.MedicationRequestDto;
import com.musala.delivery.drones.services.MedicationService;

import jakarta.validation.Valid;


@RestController
@RequestMapping("api/v1/medication")
//@ApiOperation("Medication API")
public class MedicationController {
	
	private final MedicationService medicationService;
	
	@Autowired
	private MedicationController(MedicationService medicationService) {
		this.medicationService = medicationService;
	}
	
	@GetMapping("details")
	private ResponseEntity<MedicationDto> getMedicationDetails(@RequestParam("code") String code) {
		return ResponseEntity.ok().body(medicationService.getDroneByCode(code));
	}
	
	@GetMapping("allMedicationsByDrone")
	private ResponseEntity<List<MedicationDto>> getDroneMedications(@RequestParam("droneId") long id) {
		return ResponseEntity.ok().body(medicationService.getAllMedicationsByDrone(id));
	}
	
	@PostMapping("create")
    public ResponseEntity<MedicationDto> createMedication(@Valid @RequestBody MedicationRequestDto request) {
        return ResponseEntity.ok().body(medicationService.createMedication(request));
    }
	
	
}


