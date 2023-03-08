package com.musala.delivery.drones.dto;

import com.musala.delivery.drones.enumerations.EModel;
import com.musala.delivery.drones.enumerations.EStatus;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DroneDto {

	private EModel model;
	
	private String serialNumber;

	private Integer batteryCapacity;

	private EStatus state;

	private Float weightLimit;
	
	public EModel getModel() {
		return model;
	}
	public String getSerialNumber() {
		return serialNumber;
	}
	public Integer getBatteryCapacity() {
		return batteryCapacity;
	}
	public EStatus getState() {
		return state;
	}
	public Float getWeightLimit() {
		return weightLimit;
	}
	
}
