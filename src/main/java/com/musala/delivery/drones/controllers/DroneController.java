package com.musala.delivery.drones.controllers;

import java.time.LocalDateTime;
import java.util.List;

import com.musala.delivery.drones.entities.dto.SuccessMessage;
import com.musala.delivery.drones.services.exceptions.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.musala.delivery.drones.entities.dto.DroneDto;
import com.musala.delivery.drones.entities.dto.DroneRequestDto;
import com.musala.delivery.drones.services.DroneService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("drone")
@RequiredArgsConstructor
public class DroneController {

    private final DroneService droneService;

    @GetMapping("details")
    private ResponseEntity<DroneDto> getDroneDetails(@Valid @RequestParam("serialNumber") String serialNumber)
            throws ResourceNotFoundException {
        return ResponseEntity.ok().body(droneService.getDroneBySerialNumber(serialNumber));
    }

    @GetMapping("allAvailable")
    private ResponseEntity<List<DroneDto>> getDrones() {
        return ResponseEntity.ok().body(droneService.getAllAvailableDrones());
    }

    @PostMapping("add")
    private ResponseEntity<DroneDto> addDrone(@Valid @RequestBody DroneRequestDto request)
            throws DroneAlreadyRegisteredException, InvalidRequestException, BusinessErrorException {
        return ResponseEntity.ok().body(droneService.registerDrone(request));
    }

    @GetMapping("checkBattery/{droneId}")
    private ResponseEntity<SuccessMessage> getDroneBatteryLevel(@PathVariable("droneId") long id)
            throws ResourceNotFoundException {
        return ResponseEntity.ok().body(SuccessMessage.builder()
                .code(HttpStatus.OK)
                .message("Battery level checking")
                .description("Battery level for drone id:" + id)
                .date(LocalDateTime.now())
                .value(droneService.checkDroneBatteryLevelById(id) + "%")
                .build()
        );
    }

    @PatchMapping("update")
    private ResponseEntity<DroneDto> update(@Valid @RequestBody DroneRequestDto request) throws DroneAlreadyBusyException, ResourceNotFoundException, BusinessErrorException {
        return ResponseEntity.ok().body(droneService.updateDrone(request));
    }

    @DeleteMapping("delete")
    private ResponseEntity remove(@RequestParam("serialNumber") String serialNumber) throws BusinessErrorException {
        droneService.removeDrone(serialNumber);
        return ResponseEntity.ok().body(
                SuccessMessage.builder()
                        .code(HttpStatus.OK)
                        .message("SUCCESS")
                        .value(serialNumber)
                        .description("Drone with specified code deleted")
                        .date(LocalDateTime.now())
                        .build()
        );
    }
}