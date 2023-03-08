package com.musala.delivery.drones.mappers;

import com.musala.delivery.drones.dto.DroneDto;
import com.musala.delivery.drones.entities.Drone;

public abstract class DroneMapper {

	public abstract class MedicationMapper {

		abstract Drone toEntity(DroneDto droneDto);

		abstract DroneDto toDto(Drone drone);
	}

}
