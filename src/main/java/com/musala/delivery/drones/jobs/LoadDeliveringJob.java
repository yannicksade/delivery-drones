package com.musala.delivery.drones.jobs;

import com.musala.delivery.drones.dto.HistoryDto;
import com.musala.delivery.drones.dto.HistoryRequestDto;
import com.musala.delivery.drones.entities.Drone;
import com.musala.delivery.drones.enumerations.EStatus;
import com.musala.delivery.drones.services.ActivityHistoryService;
import com.musala.delivery.drones.services.DroneService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

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
                                log.info("A drone with ID {} changed state to RETURNING", drone.getId());
                                drone.setMedications(null);
                                droneService.save(drone);
                            }
                            case RETURNING -> {
                                droneService.updateDroneStateById(drone.getId(), EStatus.IDLE);
                                log.info("A drone with ID {} changed state to IDLE", drone.getId());
                            }
                            default -> {
                            }
                        }
                }
        );
    }
}
