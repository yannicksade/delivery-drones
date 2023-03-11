package com.musala.delivery.drones.services.impl;

import com.musala.delivery.drones.entities.dto.HistoryRequestDto;
import com.musala.delivery.drones.entities.dto.LoadRequest;
import com.musala.delivery.drones.entities.dto.LoadRequestDto;
import com.musala.delivery.drones.entities.ActivityHistory;
import com.musala.delivery.drones.entities.Drone;
import com.musala.delivery.drones.entities.Medication;
import com.musala.delivery.drones.enumerations.EStatus;
import com.musala.delivery.drones.mappers.LoadMapper;
import com.musala.delivery.drones.services.ActivityHistoryService;
import com.musala.delivery.drones.services.DroneService;
import com.musala.delivery.drones.services.LoaderService;
import com.musala.delivery.drones.services.exceptions.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class LoaderServiceImpl implements LoaderService {

    private final DroneService droneService;

    private final ActivityHistoryService activityHistoryService;
    private final LoadMapper loadMapper;

    @Override
    public Integer loadDrone(Long droneId, LoadRequestDto loadRequest) throws ResourceNotFoundException, DroneAlreadyBusyException, DroneOverloadException {
        return saveLoads(droneService.findById(droneId), loadRequest);
    }

    private Integer saveLoads(Drone drone, LoadRequestDto loadRequestDto) throws DroneAlreadyBusyException, DroneOverloadException, LowBatteryException, InvalidRequestException {
        LoadRequest load = loadMapper.toLoad(loadRequestDto);
        if (!drone.getState().equals(EStatus.IDLE)) {
            throw new DroneAlreadyBusyException("Done is working already");
        }
        if (drone.getBatteryLevel() < 25) {
            throw new LowBatteryException("Done battery is very low");
        }
        double weight = 0.0d;
        if (load.getMedications() != null) {
            weight = load.getMedications().stream().map(Medication::getWeight).reduce(0.0f, Float::sum);
        }
        System.out.println(">>>>>>>>>>>>>>>>>>>: " + loadRequestDto.getMedicationId());
        if (load.getMedication() != null) {
            weight += load.getMedication().getWeight();
        }
        Double droneWeight = droneService.checkDroneLoad(Optional.ofNullable(drone));
        if (weight > droneWeight) {
            throw new DroneOverloadException("Done is Overloaded, load weight is too heavy");
        }
        if (null != load.getMedication()) {
            drone.getMedications().add(load.getMedication());
        }
        if (null != load.getMedications() && load.getMedications().size() > 0) {
            load.getMedications().forEach(e -> drone.getMedications().add(e));
        }
        validateHistoryActivityData(loadRequestDto);
        drone.getMedications().forEach(medication -> saveActivityHistory(drone, medication, loadRequestDto));
        drone.setState(EStatus.LOADING);
        log.info("A drone with ID {} is in LOADING", drone.getId());
        return droneService.save(drone).getMedications().size();
    }

    private void saveActivityHistory(Drone drone, Medication medication, HistoryRequestDto loadRequestDto) throws InvalidRequestException {
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
