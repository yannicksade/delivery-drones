package com.musala.delivery.drones.services;

import java.util.List;
import java.util.Optional;

import com.musala.delivery.drones.entities.Drone;
import com.musala.delivery.drones.entities.dto.DroneDto;
import com.musala.delivery.drones.entities.dto.DroneRequestDto;
import com.musala.delivery.drones.enumerations.EStatus;
import com.musala.delivery.drones.services.exceptions.DroneAlreadyBusyException;
import com.musala.delivery.drones.services.exceptions.DroneAlreadyRegisteredException;
import com.musala.delivery.drones.services.exceptions.InvalidRequestException;
import com.musala.delivery.drones.services.exceptions.ResourceNotFoundException;

public interface DroneService {

    Drone findById(long id);
    List<DroneDto> getAllAvailableDrones();
    List<Drone> findAllDrones();
    DroneDto registerDrone(DroneRequestDto droneRequest) throws InvalidRequestException, DroneAlreadyRegisteredException;

    DroneDto getDroneBySerialNumber(String serialNumber) throws ResourceNotFoundException;

    DroneDto validateDrone(DroneRequestDto droneRequest) throws InvalidRequestException;

    float checkDroneBatteryLevelById(long id) throws ResourceNotFoundException;

    void updateDroneStateById(long id, EStatus state);

    Double checkDroneLoad(Optional<Drone> drone);

    DroneDto updateDrone(DroneRequestDto droneRequest) throws DroneAlreadyBusyException, ResourceNotFoundException;

    Drone save(Drone drone);

    List<Drone> getWorkingDrones();

    void removeDrone(String serialNumber);
}
