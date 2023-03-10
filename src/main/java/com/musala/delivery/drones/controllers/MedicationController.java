package com.musala.delivery.drones.controllers;

import java.time.LocalDateTime;
import java.util.List;

import com.musala.delivery.drones.entities.dto.SuccessMessage;
import com.musala.delivery.drones.services.FileUploaderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.*;

import com.musala.delivery.drones.entities.dto.MedicationDto;
import com.musala.delivery.drones.entities.dto.MedicationRequestDto;
import com.musala.delivery.drones.services.exceptions.InvalidRequestException;
import com.musala.delivery.drones.services.exceptions.MedicationAlreadyRegisteredException;
import com.musala.delivery.drones.services.exceptions.ResourceNotFoundException;
import com.musala.delivery.drones.services.MedicationService;

import jakarta.validation.Valid;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping("medication")
@RequiredArgsConstructor
//@ApiOperation("Medication API")
public class MedicationController {

    private final MedicationService medicationService;

    private final FileUploaderService fileUploaderService;

    @GetMapping("details")
    private ResponseEntity<MedicationDto> getMedicationDetails(@Valid @RequestParam("code") String code) throws ResourceNotFoundException {
        return ResponseEntity.ok().body(medicationService.getDroneByCode(code));
    }

    @GetMapping("allMedicationsByDrone/{droneId}")
    private ResponseEntity<List<MedicationDto>> getDroneMedications(@PathVariable("droneId") Long droneId) {
        return ResponseEntity.ok().body(medicationService.getAllMedicationsByDrone(droneId));
    }

    @PostMapping("create")
    public ResponseEntity<MedicationDto> createMedication(@Valid @RequestBody MedicationRequestDto request, @RequestParam("file") MultipartFile multiPartFile) throws InvalidRequestException, MedicationAlreadyRegisteredException, HttpMessageNotReadableException {
        request.setImage(fileUploaderService.uploadFile(multiPartFile));
        return ResponseEntity.ok().body(medicationService.createMedication(request));
    }

    @PutMapping("update")
    private ResponseEntity<MedicationDto> update(@Valid @RequestBody MedicationRequestDto request) throws HttpMessageNotReadableException {
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


