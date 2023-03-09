package com.musala.delivery.drones.jobs;


import com.musala.delivery.drones.entities.Drone;
import com.musala.delivery.drones.services.DroneService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class BatteryCheckingJob {
    private final DroneService droneService;
    @Scheduled(cron = "0 */1 * ? * *")
    public void checkDroneBattery() {
        List<Drone> drones = droneService.findAllDrones();
        if (drones.isEmpty())
            log.info("no drones registered for battery checking");
        else
            drones.stream().forEach(drone ->  log.info("The battery level of the drone #{} is {}%", drone.getId(), drone.getBatteryLevel()));
    }
}
