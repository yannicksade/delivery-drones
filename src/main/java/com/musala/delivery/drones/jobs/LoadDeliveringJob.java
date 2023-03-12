package com.musala.delivery.drones.jobs;

import com.musala.delivery.drones.entities.dto.HistoryDto;
import com.musala.delivery.drones.entities.dto.HistoryRequestDto;
import com.musala.delivery.drones.entities.Drone;
import com.musala.delivery.drones.enumerations.EStatus;
import com.musala.delivery.drones.services.ActivityHistoryService;
import com.musala.delivery.drones.services.DroneService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class LoadDeliveringJob {
    private final DroneService droneService;
    private final ActivityHistoryService activityHistoryService;

    @Scheduled(cron = "0 */2 * ? * *")
    public void getAllDeliveringDrones() {
        List<Drone> drones = droneService.getWorkingDrones();
        log.info("{} drone(s) currently found in working state", drones.size());

        drones.stream().forEach(
                drone -> {
                    HistoryDto activityHistory = activityHistoryService.getHistoriesByDrone(drone.getSerialNumber(), HistoryRequestDto.builder().historyState(EStatus.DELIVERING).build())
                            .stream().findAny().orElse(null);
                    log.info("Activity found on Drone identified by SN #{} in {} state", drone.getSerialNumber(), drone.getState());
                    if (activityHistory != null)
                        switch (drone.getState()) {
                            case LOADING -> {
                                droneService.updateDroneStateById(drone.getId(), EStatus.LOADED);
                                log.info("A drone with SN #{} is in DELIVERING state", drone.getSerialNumber());
                                activityHistoryService.updateActivityState(activityHistory.getId(), EStatus.DELIVERING);
                                log.info("Activity history identified By #{} changed state from LOADED to DELIVERING", activityHistory.getId());
                            }
                            case LOADED -> {
                                log.info("A drone with SN #{} is loaded", drone.getSerialNumber());
                                droneService.updateDroneStateById(drone.getId(), EStatus.DELIVERING);
                                log.info("A drone with SN {} changed state from LOADED to DELIVERING", drone.getSerialNumber());
                            }
                            case DELIVERING -> {
                                droneService.updateDroneStateById(drone.getId(), EStatus.DELIVERED);
                                log.info("A Drone with SN #{} changed state to DELIVERED", drone.getSerialNumber());
                                activityHistoryService.updateActivityState(activityHistory.getId(), EStatus.DELIVERED);
                                log.info("Activity history identified By #{} changed status to DELIVERED ", activityHistory.getId());
                            }
                            case DELIVERED -> {
                                droneService.updateDroneStateById(drone.getId(), EStatus.RETURNING);
                                log.info("A drone with SN #{} changed state from DELIVERED  to RETURNING", drone.getSerialNumber());
                                drone.setMedications(new HashSet<>());
                                drone = droneService.save(drone);
                                log.info("A drone with SN #{} is discharged and mission completed", drone.getSerialNumber());
                            }
                            case RETURNING -> {
                                droneService.updateDroneStateById(drone.getId(), EStatus.IDLE);
                                log.info("A drone with SN #{} changed state from RETURNING to IDLE", drone.getSerialNumber());
                            }
                            default -> {
                            }
                        }
                }
        );
    }
}
