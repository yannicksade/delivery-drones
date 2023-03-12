package com.musala.delivery.drones.mappers;

import org.mapstruct.Mapper;
import org.springframework.stereotype.Service;

import com.musala.delivery.drones.entities.dto.DroneDto;
import com.musala.delivery.drones.entities.dto.DroneRequestDto;
import com.musala.delivery.drones.entities.Drone;

@Mapper(componentModel = "spring")
@Service
public abstract class DroneMapper {

	public abstract Drone toEntity(DroneRequestDto droneDto);

	public abstract DroneDto toDto(Drone drone);

}
