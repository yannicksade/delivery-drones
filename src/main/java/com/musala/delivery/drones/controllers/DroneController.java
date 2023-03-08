package com.musala.delivery.drones.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.musala.delivery.drones.entities.Drone;
import com.musala.delivery.drones.services.ResponseDto;

@RestController
@RequestMapping("api/v1/drone")
public class DroneController {
	
	@PostMapping("/create")
    public ResponseDto createDone(@RequestBody Drone drone) {
        //creation logic
        return null;
    }
}