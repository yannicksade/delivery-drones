package com.musala.delivery.drones.controllers;


import com.musala.delivery.drones.entities.dto.HistoryDto;
import com.musala.delivery.drones.entities.dto.HistoryRequestDto;
import com.musala.delivery.drones.services.ActivityHistoryService;
import com.musala.delivery.drones.services.exceptions.ResourceNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("history")
@RequiredArgsConstructor
public class ActivityHistoryController {

    private final ActivityHistoryService historyService;

    @GetMapping("details/{id}")
    private ResponseEntity<HistoryDto> getDetails(@PathVariable("id") long id) throws ResourceNotFoundException {
        return ResponseEntity.ok().body(historyService.getHistoryDetails(id));
    }

    @GetMapping("all")
    private ResponseEntity<List<HistoryDto>> searchAll(@Valid @RequestBody HistoryRequestDto request) {
        return ResponseEntity.ok().body(historyService.getHistories(request));
    }

    @GetMapping("drone/all/{id}")
    private ResponseEntity<List<HistoryDto>> searchAllByDrone(@Valid @RequestBody HistoryRequestDto request, @Valid  @PathVariable("id") long id) throws HttpMessageNotReadableException {
        return ResponseEntity.ok().body(historyService.getHistoriesByDrone(id, request));
    }

    @GetMapping(value = "medication/all/{id}", consumes = "application/json")
    private ResponseEntity<List<HistoryDto>> searchAllByMedication(@Valid @RequestBody HistoryRequestDto request, @PathVariable("id") long id) throws HttpMessageNotReadableException {
        return ResponseEntity.ok().body(historyService.getHistoriesByMedication(id, request));
    }
}
