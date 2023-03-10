package com.musala.delivery.drones.controllers;

import com.musala.delivery.drones.entities.dto.LoadRequestDto;
import com.musala.delivery.drones.services.exceptions.ResourceNotFoundException;
import com.musala.delivery.drones.services.LoaderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("loader")
@RequiredArgsConstructor
public class LoaderController {
    private final LoaderService loaderService;

    @PostMapping("load/{droneId}")
    private ResponseEntity<Integer> loadDrone(@Valid @PathVariable("droneId") Long id, @Valid() @RequestBody LoadRequestDto request) throws ResourceNotFoundException, HttpMessageNotReadableException {
        return ResponseEntity.ok().body(loaderService.loadDrone(id, request));
    }
}
