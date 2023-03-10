package com.musala.delivery.drones.controllers;

import com.musala.delivery.drones.dto.LoadRequestDto;
import com.musala.delivery.drones.exceptions.ResourceNotFoundException;
import com.musala.delivery.drones.services.LoaderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("loader")
@RequiredArgsConstructor
public class LoaderController {
    private final LoaderService loaderService;

    @PostMapping("load/{droneId}")
    private ResponseEntity<Integer> loadDrone(@Valid @PathVariable("droneId") Long id, @RequestBody LoadRequestDto request) throws ResourceNotFoundException {
        return ResponseEntity.ok().body(loaderService.loadDrone(id, request));
    }
}
