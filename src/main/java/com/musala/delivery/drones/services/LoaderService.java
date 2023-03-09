package com.musala.delivery.drones.services;

import com.musala.delivery.drones.dto.LoadRequestDto;
import com.musala.delivery.drones.exceptions.ResourceNotFoundException;

public interface LoaderService {
    Integer loadDrone(Long droneId, LoadRequestDto load) throws ResourceNotFoundException;
}
