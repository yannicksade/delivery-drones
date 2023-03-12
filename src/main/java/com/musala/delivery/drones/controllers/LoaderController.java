package com.musala.delivery.drones.controllers;

import com.musala.delivery.drones.entities.dto.LoadRequestDto;
import com.musala.delivery.drones.entities.dto.SuccessMessage;
import com.musala.delivery.drones.exceptions.ResourceNotFoundException;
import com.musala.delivery.drones.services.LoaderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
@RequestMapping("loader")
public class LoaderController {
    private final LoaderService loaderService;

    @PostMapping("load/drone/{serialNumber}")
    private ResponseEntity<SuccessMessage> loadDrone(@PathVariable("serialNumber") String serialNumber, @Valid() @RequestBody LoadRequestDto request) throws ResourceNotFoundException, HttpMessageNotReadableException {
        return ResponseEntity.ok().body(
                SuccessMessage.builder()
                        .code(HttpStatus.OK)
                        .message("Load quantity")
                        .value(loaderService.loadDrone(serialNumber, request) + " item(s) loaded")
                        .description("show the number of item loaded in the drone")
                        .date(LocalDateTime.now())
                        .build());
    }

    @GetMapping("check/load/{serialNumber}")
    public ResponseEntity<SuccessMessage> checkDroneLoad(@PathVariable("serialNumber") String serialNumber) {
        return ResponseEntity.ok().body(
                SuccessMessage.builder()
                        .code(HttpStatus.OK)
                        .message("Checking Drone Load")
                        .value(loaderService.checkDroneLoad(serialNumber) + " gramme(s)")
                        .description("show total weight of drone")
                        .date(LocalDateTime.now())
                        .build()
        );
    }
}
