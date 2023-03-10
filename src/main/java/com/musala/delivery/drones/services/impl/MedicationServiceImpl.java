package com.musala.delivery.drones.services.impl;

import java.awt.Image;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javax.imageio.ImageIO;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.musala.delivery.drones.dto.MedicationDto;
import com.musala.delivery.drones.dto.MedicationRequestDto;
import com.musala.delivery.drones.entities.Medication;
import com.musala.delivery.drones.services.exceptions.InvalidRequestException;
import com.musala.delivery.drones.services.exceptions.MedicationAlreadyRegisteredException;
import com.musala.delivery.drones.services.exceptions.ResourceNotFoundException;
import com.musala.delivery.drones.mappers.MedicationMapper;
import com.musala.delivery.drones.repositories.MedicationRepository;
import com.musala.delivery.drones.services.MedicationService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class MedicationServiceImpl implements MedicationService {

	private final MedicationRepository medicationRepository;

	private final MedicationMapper medicationMapper;

	@Override
	public List<MedicationDto> getAllMedicationsByDrone(long droneId) {
		return medicationRepository.findAll().stream().map(medicationMapper::toDto).collect(Collectors.toList());
	}

	@Override
	public MedicationDto createMedication(MedicationRequestDto request)
			throws InvalidRequestException, MedicationAlreadyRegisteredException {
		Optional<Medication> medecine = medicationRepository.findByName(request.getName());
		if (medecine.isPresent()) {
			throw new MedicationAlreadyRegisteredException("the provided medication item is already registered");
		}
		MedicationDto medicationDto = validateMedication(request);
		log.info("A medication with code {} and name {} is saving...", request.getCode(), request.getName());
		return medicationMapper.toDto(medicationRepository.save(
					Medication.builder()
					.code(medicationDto.getCode())
					.name(medicationDto.getName())
					.weight(medicationDto.getWeight())
					.image(medicationDto.getImage())
					.build()
				));
	}

	@Override
	public MedicationDto updateMedication(MedicationRequestDto request) {
		return medicationMapper.toDto(medicationRepository.save(medicationMapper.toEntity(request)));
		
	}
	
	@Override
	public MedicationDto getDroneByCode(String code) throws ResourceNotFoundException {
		return medicationMapper.toDto(medicationRepository.findByCode(code)
				.orElseThrow(() -> new ResourceNotFoundException("No medication available with the code: " + code)));
	}
	@Override
	public MedicationDto validateMedication(MedicationRequestDto request) throws InvalidRequestException {
		if (request.equals(null)) {
			throw new InvalidRequestException("Invalid data: " + null);
		} if(request.getName().isBlank()) {
			throw new InvalidRequestException("Medication Name is required");
		} else if(!Pattern.matches("^[A-Za-z0-9-_]+$", request.getName())) {
			throw new InvalidRequestException("Name must be composed solely of letters, numbers, hiphen and/or underscore");
		} else if (request.getWeight() <= 0.0) {
			throw new InvalidRequestException("medication weight must be greater than 0.0");
		} else if(!Pattern.matches("^[A-Z0-9_]+$", request.getCode())) {
			throw new InvalidRequestException("Code must be composed of solely UPPERCASE letters, underscores and numbers");
		}
		validateImageUrl(request.getName());

		return medicationMapper.toDto(medicationMapper.toEntity(request));
	}

	private void validateImageUrl(String imageUrl) throws InvalidRequestException {
		try {
			Image image = ImageIO.read(new URL(imageUrl));
			if (image == null) {
				throw new InvalidRequestException("No image url found for this medication");
			}
		} catch (IOException ioe) {
			throw new InvalidRequestException("Invalid image url or path");
		}
	}
}
