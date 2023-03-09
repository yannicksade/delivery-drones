package com.musala.delivery.drones.controllers;


import com.musala.delivery.drones.dto.DroneDto;
import com.musala.delivery.drones.dto.DroneRequestDto;
import com.musala.delivery.drones.dto.HistoryDto;
import com.musala.delivery.drones.dto.HistoryRequestDto;
import com.musala.delivery.drones.services.ActivityHistoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("history")
@RequiredArgsConstructor
public class ActivityHistoryController {

    private final ActivityHistoryService historyService;

    @GetMapping("all")
    private ResponseEntity<List<HistoryDto>> searchAll(@Valid @RequestBody HistoryRequestDto request) {
        return ResponseEntity.ok().body(historyService.getHistories(request));
    }
    @GetMapping("drone/all/{id}")
    private ResponseEntity<List<HistoryDto>> searchAllByDrone(@Valid @RequestBody HistoryRequestDto request, @PathVariable("id") long id) {
        return ResponseEntity.ok().body(historyService.getHistoriesByDrone(id, request));
    }
    @GetMapping("medication/all/{id}")
    private ResponseEntity<List<HistoryDto>> searchAllByMedication(@Valid @RequestBody HistoryRequestDto request, @PathVariable("id") long id) {
        return ResponseEntity.ok().body(historyService.getHistoriesByMedication(id, request));
    }
}
