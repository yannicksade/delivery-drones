package com.musala.delivery.drones.configs;

import org.springframework.context.annotation.Bean;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition
public class ApiDocConfig {

	@Bean
	public OpenAPI baseOpenApi() {
		return new OpenAPI().info(new Info().title("Delivering drones docs").version("1.0.0")
				.description("Musala drones App for delivering medication"));
	}
}
