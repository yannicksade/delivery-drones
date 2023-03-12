package com.musala.delivery.drones.controllers;

import java.time.LocalDateTime;
import java.util.List;

import com.musala.delivery.drones.entities.dto.*;
import com.musala.delivery.drones.exceptions.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    private ResponseEntity<List<DroneDto>> getAvailableDrones() {
        return ResponseEntity.ok().body(droneService.getAllAvailableDrones());
    }
    @GetMapping("list/all")
    private ResponseEntity<List<DroneDto>> getMedications(@Valid @RequestBody DroneRequestDto request) {
        return ResponseEntity.ok().body(droneService.listAllDrones(request));
    }
    @PostMapping("add")
    private ResponseEntity<DroneDto> addDrone(@Valid @RequestBody DroneRequestDto request)
            throws DroneAlreadyRegisteredException, InvalidRequestException, BusinessErrorException {
        return ResponseEntity.ok().body(droneService.registerDrone(request));
    }

    @GetMapping("{serialNumber}/checkBattery")
    private ResponseEntity<SuccessMessage> getDroneBatteryLevel(@PathVariable("serialNumber") String serialNumber)
            throws ResourceNotFoundException {
        return ResponseEntity.ok().body(SuccessMessage.builder()
                .code(HttpStatus.OK)
                .message("Battery level checking")
                .description("Battery level for drone SN :" + serialNumber)
                .date(LocalDateTime.now())
                .value(droneService.checkDroneBatteryLevelById(serialNumber) + "%")
                .build()
        );
    }

    @PatchMapping("update")
    private ResponseEntity<DroneDto> update(@Valid @RequestBody DroneRequestDto request) throws DroneAlreadyBusyException, ResourceNotFoundException, BusinessErrorException {
        return ResponseEntity.ok().body(droneService.updateDrone(request));
    }

    @DeleteMapping("delete")
    private ResponseEntity<SuccessMessage> remove(@RequestParam("serialNumber") String serialNumber) throws BusinessErrorException {
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