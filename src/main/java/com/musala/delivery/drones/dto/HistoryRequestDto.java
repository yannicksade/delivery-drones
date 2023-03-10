package com.musala.delivery.drones.dto;

import com.musala.delivery.drones.enumerations.EStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class HistoryRequestDto {
    private EStatus historyState;
    private String originLocation;
    private String destinationLocation;
    private LocalDateTime startedAt;
}
