package com.musala.delivery.drones.entities.dto;

import com.musala.delivery.drones.enumerations.EModel;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
public class DroneRequestDto {
    private String serialNumber;
    private EModel model;
    private Double weightLimit;

}
