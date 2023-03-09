package com.musala.delivery.drones.services.impl;

import com.musala.delivery.drones.dto.LoadRequest;
import com.musala.delivery.drones.dto.LoadRequestDto;
import com.musala.delivery.drones.entities.Drone;
import com.musala.delivery.drones.exceptions.ResourceNotFoundException;
import com.musala.delivery.drones.mappers.LoadMapper;
import com.musala.delivery.drones.services.DroneService;
import com.musala.delivery.drones.services.LoaderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class LoaderServiceImpl implements LoaderService {

    private final DroneService droneService;

    private final LoadMapper loadMapper;

    @Override
    public Integer loadDrone(Long droneId, LoadRequestDto loadRequest) throws ResourceNotFoundException {
        Drone drone = droneService.findById(droneId).orElseThrow(() -> new ResourceNotFoundException("No drone available"));
        return saveLoads(drone, loadRequest);
    }
    private Integer saveLoads(Drone drone, LoadRequestDto loadRequest) {
        LoadRequest load = loadMapper.toLoad(loadRequest);
        if (null != load.getMedication()) {
            drone.getMedications().add(load.getMedication());
        }
        if (null != load.getMedications() && load.getMedications().size() > 0) {
            load.getMedications().forEach(e -> drone.getMedications().add(e));
        }
        return droneService.save(drone).getMedications().size();
    }

}
