package com.musala.delivery.drones.services.Implements;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.GenericPropertyMatcher;
import org.springframework.data.domain.ExampleMatcher.MatcherConfigurer;
import org.springframework.data.repository.query.parser.Part.IgnoreCaseType;
import org.springframework.stereotype.Service;

import com.musala.delivery.drones.dto.MedicationDto;
import com.musala.delivery.drones.dto.MedicationRequestDto;
import com.musala.delivery.drones.entities.Medication;
import com.musala.delivery.drones.exceptions.InvalidRequestException;
import com.musala.delivery.drones.exceptions.MedicationAlreadyRegisteredException;
import com.musala.delivery.drones.exceptions.ResourceNotFoundException;
import com.musala.delivery.drones.repositories.MedicationRepository;
import com.musala.delivery.drones.services.MedicationService;

@Service
public class MedicationServiceImpl implements MedicationService {

	private final MedicationRepository medicationRepository;
	
	@Autowired
	MedicationServiceImpl(MedicationRepository medicationRepository) {
		this.medicationRepository = medicationRepository;
	}
	
	@Override
	public List<MedicationDto> getAllMedicationsByDrone(long droneId) {
		return medicationRepository.findAll().stream().map(this::toMedicationDto).collect(Collectors.toList());
	}
	private MedicationDto toMedicationDto(Medication data) {
		return Medication.builder()
				.code(data.getCode())
				.name(data.getName())
				.weight(data.getWeight())
				.image(data.getImage())
				.droneId(data.getDrone().getId()).build();
	}

	private Medication toMedication(MedicationDto data) {
		return MedicationDto.builder()
				.code(data.getCode())
				.name(data.getName())
				.weight(data.getWeight())
				.image(data.getImage())
				.drone(medicationRepository.findById(medicationRepository.findById(data.getDroneId())
						.get().orElse(null))).build();
	}
	
	@Override
	public MedicationDto createMedication(MedicationRequestDto request)
			throws InvalidRequestException, MedicationAlreadyRegisteredException {
		ExampleMatcher exmMatcher = ExampleMatcher.matching();
		exmMatcher.withIgnorePaths("id").withMatcher("name", GenericPropertyMatcher.of(null));
		Example<Medication> example = Example.of(new Medication(request.getName()));
		if(medicationRepository.exists(example)) throw new MedicationAlreadyRegisteredException();
		return toMedicationDto(medicationRepository.save(toMedication(request)));
	}

	@Override
	public MedicationDto getDroneByCode(String code) throws ResourceNotFoundException {
		Optional<Medication> medication = medicationRepository.findByCode(code);
				if(medication.isPresent()) return toMedicationDto(medication.get());
				 throw new ResourceNotFoundException();
	}

	@Override
	public MedicationDto validateMedication(MedicationRequestDto request)
			throws InvalidRequestException, ResourceNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

}
