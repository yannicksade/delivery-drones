package com.musala.delivery.drones.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.musala.delivery.drones.entities.Medication;
import com.musala.delivery.drones.services.ResponseDto;


@RestController
@RequestMapping("api/v1/medication")
//@ApiOperation("Medication API")
public class MedicationController {
	
	@PostMapping("/create")
    public ResponseDto createDone(@RequestBody Medication medication) {
        //creation logic
        return null;
    }
}


