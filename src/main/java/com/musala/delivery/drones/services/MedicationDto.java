package com.musala.delivery.drones.services;

import java.io.Serializable;

import lombok.Data;

@Data
public class MedicationDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String code;

	private String name;

	private Float weight;

	private String image;

	private Long droneId;
}
