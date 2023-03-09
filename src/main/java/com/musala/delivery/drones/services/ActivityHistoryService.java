package com.musala.delivery.drones.services;

import com.musala.delivery.drones.dto.HistoryDto;
import com.musala.delivery.drones.dto.HistoryRequestDto;
import com.musala.delivery.drones.entities.ActivityHistory;
import com.musala.delivery.drones.enumerations.EStatus;

import java.util.List;

public interface ActivityHistoryService {

   List<HistoryDto> getHistories(HistoryRequestDto requestDto);

    List<HistoryDto> getHistoriesByDrone(long droneID, HistoryRequestDto requestDto);

    List<HistoryDto> getHistoriesByMedication(long medicationID, HistoryRequestDto requestDto);

    void updateActivityState(long id, EStatus status);

    ActivityHistory createHistory(ActivityHistory activityHistory);
}
