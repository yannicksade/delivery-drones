package com.musala.delivery.drones.entities.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ErrorMessage implements Serializable {
	private String description;
	private String message;
	private HttpStatus errorCode;
	LocalDateTime date;
	
}
