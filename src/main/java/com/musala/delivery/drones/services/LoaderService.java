package com.musala.delivery.drones.services;

import com.musala.delivery.drones.entities.Drone;
import com.musala.delivery.drones.entities.dto.LoadRequestDto;
import com.musala.delivery.drones.exceptions.*;

import java.util.Optional;

public interface LoaderService {
    Integer loadDrone(String serialNumber, LoadRequestDto load) throws ResourceNotFoundException, DroneAlreadyBusyException, DroneOverloadException, LowBatteryException, InvalidRequestException;

    Double checkDroneLoad(String serialNumber);
}
