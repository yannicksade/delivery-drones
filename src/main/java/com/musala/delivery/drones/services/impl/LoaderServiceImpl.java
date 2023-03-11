package com.musala.delivery.drones.services.impl;

import com.musala.delivery.drones.entities.dto.LoadRequestDto;
import com.musala.delivery.drones.entities.ActivityHistory;
import com.musala.delivery.drones.entities.Drone;
import com.musala.delivery.drones.entities.Medication;
import com.musala.delivery.drones.entities.dto.MedicationDto;
import com.musala.delivery.drones.enumerations.EStatus;
import com.musala.delivery.drones.exceptions.*;
import com.musala.delivery.drones.mappers.MedicationMapper;
import com.musala.delivery.drones.services.ActivityHistoryService;
import com.musala.delivery.drones.services.DroneService;
import com.musala.delivery.drones.services.LoaderService;
import com.musala.delivery.drones.services.MedicationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class LoaderServiceImpl implements LoaderService {

    private final DroneService droneService;

    private final ActivityHistoryService activityHistoryService;

    private final MedicationService medicationService;

    private final MedicationMapper medicationMapper;

    @Override
    public Integer loadDrone(Long droneId, LoadRequestDto loadRequest) throws ResourceNotFoundException, DroneAlreadyBusyException, DroneOverloadException {
        return saveLoads(droneService.findById(droneId), loadRequest);
    }

    private Integer saveLoads(Drone drone, LoadRequestDto loadRequestDto) throws DroneAlreadyBusyException, DroneOverloadException, LowBatteryException, InvalidRequestException {
        log.info("Loading for the drone {} started", drone.getId());
        if (!drone.getState().equals(EStatus.IDLE)) {
            throw new DroneAlreadyBusyException("Done is working already");
        }
        if (drone.getBatteryLevel() < 25) {
            throw new LowBatteryException("Done battery is very low");
        }
        double weight = 0.0d;
        MedicationDto medicationDto = medicationService.getMedicationByCode(loadRequestDto.getMedicationCode());
        weight += medicationDto.getWeight();
        List<MedicationDto> medicationDtoList = new ArrayList<>();
        medicationDtoList.add(medicationDto);
        weight += loadRequestDto.getMedicationCodeList().stream().map(e -> {
            MedicationDto dto = medicationService.getMedicationByCode(e);
            medicationDtoList.add(dto);
            return dto;
        }).map(MedicationDto::getWeight).reduce(0.0f, Float::sum);
        log.info("Total load weight estimated: {} gramme(s)", weight);
        Double droneWeight = droneService.checkDroneLoad(Optional.ofNullable(drone));
        if (weight > droneWeight) {
            throw new DroneOverloadException("Done is Overloaded, load weight is too heavy");
        }
        log.info("validating the origin and destination");
        validateHistoryActivityData(loadRequestDto);
        medicationDtoList.forEach(dto -> {
            Medication medication = medicationMapper.toEntity(medicationMapper.toDto(dto));
            drone.getMedications().add(valiadte(medication));
            saveActivityHistory(drone, medication, loadRequestDto);
        });

        drone.setState(EStatus.LOADING);
        log.info("The drone with ID {} finished LOADING, final added weight is {} gramme(s)", weight);
        droneService.save(drone);
        return medicationService.getAllMedicationsByDrone(drone.getId()).size();
    }

    private Medication valiadte(Medication medication) throws MedicationLoadedAlreadyException {
        if (medicationService.checkIfMedicationIsLoadedOrDelivered(medication.getCode())) {
            throw new MedicationLoadedAlreadyException("Cannot be loaded");
        }
        //the medication must not be in to another drone already
        return medication;
    }

    private void saveActivityHistory(Drone drone, Medication medication, LoadRequestDto loadRequestDto) throws InvalidRequestException {
        ActivityHistory history = activityHistoryService.createHistory(
                ActivityHistory.builder()
                        .historyState(EStatus.DELIVERING)
                        .drone(drone)
                        .medication(medication)
                        .originLocation(loadRequestDto.getOriginLocation())
                        .destinationLocation(loadRequestDto.getDestinationLocation())
                        .startedAt(LocalDateTime.now())
                        .build()
        );
        log.info("An activity History with ID {} is created for medication identified by {} in DELIVERING state", history.getId(), medication.getId());
    }

    private void validateHistoryActivityData(LoadRequestDto loadRequestDto) {
        if (loadRequestDto.getOriginLocation().isBlank()) {
            throw new InvalidRequestException("Origin is Required");
        }
        if (loadRequestDto.getDestinationLocation().isBlank()) {
            throw new InvalidRequestException("Destination is Required");
        }
    }
}
