package com.musala.delivery.drones.controllers;

import java.time.LocalDateTime;
import java.util.List;

import com.musala.delivery.drones.entities.dto.SuccessMessage;
import com.musala.delivery.drones.exceptions.BusinessErrorException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.musala.delivery.drones.entities.dto.MedicationDto;
import com.musala.delivery.drones.entities.dto.MedicationRequestDto;
import com.musala.delivery.drones.exceptions.InvalidRequestException;
import com.musala.delivery.drones.exceptions.MedicationAlreadyRegisteredException;
import com.musala.delivery.drones.exceptions.ResourceNotFoundException;
import com.musala.delivery.drones.services.MedicationService;

import jakarta.validation.Valid;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping("medication")
@RequiredArgsConstructor
//@ApiOperation("Medication API")
public class MedicationController {

    private final MedicationService medicationService;

    @GetMapping("details")
    private ResponseEntity<MedicationDto> getMedicationDetails(@Valid @RequestParam("code") String code) throws ResourceNotFoundException {
        return ResponseEntity.ok().body(medicationService.getMedicationByCode(code));
    }

    @GetMapping("byDrone/{serialNumber}/all")
    private ResponseEntity<List<MedicationDto>> getDroneMedications(@PathVariable("serialNumber") String droneId) {
        return ResponseEntity.ok().body(medicationService.getAllMedicationsByDrone(droneId));
    }

    @GetMapping("list/all")
    private ResponseEntity<List<MedicationDto>> getMedications(@Valid @RequestBody MedicationRequestDto request) {
        return ResponseEntity.ok().body(medicationService.listAllMedications(request));
    }
    @PostMapping("create")
    public ResponseEntity<MedicationDto> createMedication(@Valid @RequestBody MedicationRequestDto request) throws InvalidRequestException, MedicationAlreadyRegisteredException, BusinessErrorException {
        return ResponseEntity.ok().body(medicationService.createMedication(request));
    }

    @PostMapping("{medicationCode}/update/image")
    public ResponseEntity<MedicationDto> updateImage(@PathVariable("medicationCode") String code, @RequestParam("file") MultipartFile multiPartFile) throws ResourceNotFoundException, InvalidRequestException {
        return ResponseEntity.ok().body(medicationService.updatedImage(code, multiPartFile));
    }
    @PatchMapping("update")
    private ResponseEntity<MedicationDto> update(@Valid @RequestBody MedicationRequestDto request) throws BusinessErrorException {
        return ResponseEntity.ok().body(medicationService.updateMedication(request));
    }

    @DeleteMapping("delete")
    private ResponseEntity<SuccessMessage> remove(@Valid @RequestParam("code") String code) {
        medicationService.removeMedication(code);
        return ResponseEntity.ok().body(
                SuccessMessage.builder()
                        .code(HttpStatus.OK)
                        .message("SUCCESS")
                        .value(code)
                        .description("Medication with specified code deleted ")
                        .date(LocalDateTime.now())
                        .build()
        );
    }
}


