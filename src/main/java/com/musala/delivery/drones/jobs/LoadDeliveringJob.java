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
                    HistoryDto activityHistory = activityHistoryService.getHistoriesByDrone(drone.getId(), HistoryRequestDto.builder().historyState(EStatus.DELIVERING).build())
                            .stream().findAny().orElse(null);
                    log.info("Activity found on Drone identified by ID {} in {} state", drone.getId(), drone.getState());
                    if (activityHistory != null)
                      switch (drone.getState()) {
                            case LOADING -> {
                                droneService.updateDroneStateById(drone.getId(), EStatus.LOADED);
                                log.info("A drone with ID {} is in DELIVERING state", drone.getId());
                                activityHistoryService.updateActivityState(activityHistory.getId(), EStatus.DELIVERING);
                                log.info("Activity history identified By {} changed state from LOADED to DELIVERING", activityHistory.getId());
                            }
                            case LOADED -> {

                                log.info("A drone with ID {} is loaded, total load {}g ", drone.getId(), droneService.checkDroneLoad(Optional.of(drone)));
                                droneService.updateDroneStateById(drone.getId(), EStatus.DELIVERING);
                                log.info("A drone with ID {} changed state from LOADED to DELIVERING", drone.getId());
                            }
                            case DELIVERING -> {
                                droneService.updateDroneStateById(drone.getId(), EStatus.DELIVERED);
                                log.info("A Drone with ID {} changed state to DELIVERED", drone.getId());
                                activityHistoryService.updateActivityState(activityHistory.getId(), EStatus.DELIVERED);
                                log.info("Activity history identified By {} changed status to DELIVERED ", activityHistory.getId());
                            }
                            case DELIVERED -> {
                                droneService.updateDroneStateById(drone.getId(), EStatus.RETURNING);
                                log.info("A drone with ID {} changed state from DELIVERED  to RETURNING", drone.getId());
                                drone.setMedications(new HashSet<>());
                                drone = droneService.save(drone);
                                log.info("A drone with ID {} is unloaded, remaining load {}g ", drone.getId(), droneService.checkDroneLoad(Optional.of(drone)));
                            }
                            case RETURNING -> {
                                droneService.updateDroneStateById(drone.getId(), EStatus.IDLE);
                                log.info("A drone with ID {} changed state from RETURNING to IDLE", drone.getId());
                            }
                            default -> {
                            }
                        }
                }
        );
    }
}
