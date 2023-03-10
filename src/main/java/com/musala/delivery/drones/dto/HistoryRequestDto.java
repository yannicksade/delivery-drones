package com.musala.delivery.drones.dto;

import com.musala.delivery.drones.enumerations.EStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HistoryRequestDto {
    private EStatus historyState;
    private String originLocation;
    private String destinationLocation;
    private LocalDateTime startedAt;
}
