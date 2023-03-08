package com.musala.delivery.drones.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
public class ErrorMessage implements Serializable {
	
	private int errorCode;
	private String description;
	private String message;
	LocalDateTime date;
	private Object value;
	
}
