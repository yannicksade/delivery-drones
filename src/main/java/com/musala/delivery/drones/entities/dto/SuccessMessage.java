package com.musala.delivery.drones.entities.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SuccessMessage implements Serializable {
	
	private HttpStatus code;
	private String description;
	private String message;
	LocalDateTime date;
	private Object value;
	
}
