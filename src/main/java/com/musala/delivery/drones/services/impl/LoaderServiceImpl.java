package com.musala.delivery.drones.services.impl;

import com.musala.delivery.drones.entities.dto.LoadRequestDto;
import com.musala.delivery.drones.entities.ActivityHistory;
import com.musala.delivery.drones.entities.Drone;
import com.musala.delivery.drones.entities.Medication;
import com.musala.delivery.drones.entities.dto.MedicationDto;
import com.musala.delivery.drones.enumerations.EStatus;
import com.musala.delivery.drones.exceptions.*;
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
import java.util.Random;

@Slf4j
@Service
@RequiredArgsConstructor
public class LoaderServiceImpl implements LoaderService {

    private final DroneService droneService;

    private final ActivityHistoryService activityHistoryService;

    private final MedicationService medicationService;


    @Override
    public Integer loadDrone(String serialNumber, LoadRequestDto loadRequest) throws ResourceNotFoundException, DroneAlreadyBusyException, DroneOverloadException {
        return saveLoads(droneService.findBySerialNumber(serialNumber), loadRequest);
    }

    private Integer saveLoads(Drone drone, LoadRequestDto loadRequestDto) throws DroneAlreadyBusyException, DroneOverloadException, LowBatteryException, InvalidRequestException {
        log.info("Loading started for the drone #{}...", drone.getId());
        if (!drone.getState().equals(EStatus.IDLE)) {
            throw new DroneAlreadyBusyException("Drone is working already");
        }
        if (drone.getBatteryLevel() < 25) {
            throw new LowBatteryException("Drone battery is very low");
        }
        double weight = 0.0d;
        List<MedicationDto> medicationDtoList = new ArrayList<>();
        weight += loadRequestDto.getMedicationCodeList().stream().map(e -> {
            MedicationDto dto = medicationService.getMedicationByCode(e);
            medicationDtoList.add(dto);
            return dto;
        }).map(MedicationDto::getWeight).reduce(0.0d, Double::sum);
        Double droneWeight = checkDroneLoad(drone.getSerialNumber());
        if (weight + droneWeight > drone.getWeightLimit()) {
            log.info("Load weight estimated: {} gramme(s), actual drone weight: {} gramme(s) ", weight, droneWeight);
            throw new DroneOverloadException("Drone is Overloaded, load weight is too heavy");
        }
        log.info("Total load weight estimated after loading: {} gramme(s)", weight + droneWeight);

        validateHistoryActivityData(loadRequestDto);
        log.info("drone with SN #{} is travelling from the origin: {} to the destination: {}", drone.getSerialNumber(), loadRequestDto.getOriginLocation(), loadRequestDto.getDestinationLocation());
        medicationDtoList.forEach(dto -> {
            Medication medication = medicationService.findByCode(dto.getCode());//medicationMapper.toEntity(medicationMapper.toDto(dto));
            drone.getMedications().add(validate(medication));
            saveActivityHistory(drone, medication, loadRequestDto);
        });

        log.info("The drone with SN #{} are finished LOADING, final added weight is {} gramme(s)", drone.getSerialNumber(), weight);
        drone.setState(EStatus.LOADING);
        droneService.save(drone);
        return medicationService.getAllMedicationsByDrone(drone.getSerialNumber()).size();
    }

    private Medication validate(Medication medication) throws MedicationLoadedAlreadyException {
        if (medicationService.checkIfMedicationIsLoadedOrDelivered(medication.getCode())) {
            throw new MedicationLoadedAlreadyException("Cannot load again, medication with that code is loaded in another drone or has been shipped already.");
        }

        return medication;
    }

    private void saveActivityHistory(Drone drone, Medication medication, LoadRequestDto loadRequestDto) throws InvalidRequestException {
        ActivityHistory history = activityHistoryService.createHistory(
                ActivityHistory.builder()
                        .id(new Random().nextLong(0, 100))
                        .historyState(EStatus.DELIVERING)
                        .drone(drone)
                        .medication(medication)
                        .originLocation(loadRequestDto.getOriginLocation())
                        .destinationLocation(loadRequestDto.getDestinationLocation())
                        .startedAt(LocalDateTime.now())
                        .build()
        );
        log.info("An activity History with ID #{} is created for medication identified by #{} in DELIVERING state", history.getId(), medication.getCode());
    }

    private void validateHistoryActivityData(LoadRequestDto loadRequestDto) {
        if (loadRequestDto.getOriginLocation().isBlank()) {
            throw new InvalidRequestException("Origin is Required");
        }
        if (loadRequestDto.getDestinationLocation().isBlank()) {
            throw new InvalidRequestException("Destination is Required");
        }
    }

    @Override
    public Double checkDroneLoad(String serialNumber) {
        return medicationService.getAllMedicationsByDrone(serialNumber).stream().map(MedicationDto::getWeight).reduce(0.0d, Double::sum);
    }
}
/*
 if (checkDroneLoad(drone) > 500.0d) {

            throw new DroneOverloadException("Drone weight limit is reached");
        }
*/