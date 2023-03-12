package com.musala.delivery.drones.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import com.musala.delivery.drones.enumerations.EStatus;
import com.musala.delivery.drones.exceptions.*;
import com.musala.delivery.drones.services.FileUploaderService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.musala.delivery.drones.entities.dto.MedicationDto;
import com.musala.delivery.drones.entities.dto.MedicationRequestDto;
import com.musala.delivery.drones.entities.Medication;
import com.musala.delivery.drones.mappers.MedicationMapper;
import com.musala.delivery.drones.repositories.MedicationRepository;
import com.musala.delivery.drones.services.MedicationService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
@RequiredArgsConstructor
public class MedicationServiceImpl implements MedicationService {

    private final MedicationRepository medicationRepository;

    private final MedicationMapper medicationMapper;

    private final FileUploaderService fileUploaderService;

    @PersistenceContext
    private final EntityManager entityManager;

    @Override
    public List<MedicationDto> getAllMedicationsByDrone(String serialNumber) {
        return medicationRepository.findAllByDrone(serialNumber).stream().map(medicationMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public List<MedicationDto> listAllMedications(MedicationRequestDto requestDto) {
        return Optional.ofNullable(entityManager.createQuery(getCriteria(requestDto)).getResultList()).orElse(new ArrayList<>())
                .stream().map(medicationMapper::toDto).collect(Collectors.toList());
    }

    private CriteriaQuery<Medication> getCriteria(MedicationRequestDto requestDto) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Medication> query = cb.createQuery(Medication.class);
        List<Predicate> predicates = new ArrayList<>();
        Root<Medication> medication = query.from(Medication.class);
        if (requestDto.getName() != null)
            predicates.add(cb.equal(medication.get("name"), requestDto.getName()));
        if (requestDto.getCode() != null)
            predicates.add(cb.like(medication.get("code"), requestDto.getCode()));
        int nb = predicates.size();
        return query.select(medication)
                .where(cb.and(predicates.toArray(new Predicate[nb])));
    }

    @Override
    public MedicationDto createMedication(MedicationRequestDto request)
            throws InvalidRequestException, MedicationAlreadyRegisteredException, BusinessErrorException {
        MedicationDto medicationDto = validateMedication(request);
        try {
            medicationDto = medicationMapper.toDto(medicationRepository.save(
                    Medication.builder()
                            .id(new Random().nextLong(10, 100))
                            .code(medicationDto.getCode())
                            .name(medicationDto.getName())
                            .weight(medicationDto.getWeight())
                            .image(medicationDto.getImage())
                            .build()
            ));
        } catch (DataAccessException ex) {
            throw new BusinessErrorException("Medication identified by code may exist already - " + ex.getMostSpecificCause().getMessage());
        }
        log.info("A medication with code #{} and name {} is saving", request.getCode(), request.getName());
        return medicationDto;
    }

    @Override
    public MedicationDto updateMedication(MedicationRequestDto request) throws BusinessErrorException {
        validateMedication(request);
        Medication medication = medicationRepository.findByCode(request.getCode()).orElseThrow(() -> new ResourceNotFoundException("No Medication exist with that code"));
        if (!medication.getName().equals(request.getName())) medication.setName(request.getName());
        if (!medication.getWeight().equals(request.getWeight())) medication.setWeight(request.getWeight());
        if (!medication.getImage().equals(request.getName())) medication.setImage(request.getImage());
        log.info("A Medication with code #{} is updated.", request.getCode());
        try {
            medication = medicationRepository.save(medication);
        } catch (DataAccessException ex) {
            throw new BusinessErrorException("Medication identified by code - " + ex.getMostSpecificCause().getMessage());
        }
        return medicationMapper.toDto(medication);
    }

    @Override
    public MedicationDto getMedicationByCode(String code) throws ResourceNotFoundException {
        return medicationMapper.toDto(medicationRepository.findByCode(code)
                .orElseThrow(() -> new ResourceNotFoundException("No medication available with the code: " + code)));
    }

    @Override
    public MedicationDto validateMedication(MedicationRequestDto request) throws InvalidRequestException {
        if (request.getName().isBlank()) {
            throw new InvalidRequestException("Medication Name is required");
        } else if (!Pattern.matches("^[A-Za-z\\d-_]+$", request.getName())) {
            throw new InvalidRequestException("Name must be composed solely of letters, numbers, hiphen and/or underscore");
        } else if (request.getWeight() <= 0.0) {
            throw new InvalidRequestException("medication weight must be greater than 0.0");
        } else if (!Pattern.matches("^[A-Z\\d_]+$", request.getCode())) {
            throw new InvalidRequestException("Code must be composed of solely UPPERCASE letters, underscores and numbers");
        }

        return medicationMapper.toDto(medicationMapper.toEntity(request));
    }

    @Override
    public void removeMedication(String code) throws BusinessErrorException {
        //remove only when drone not delivering yet
        if (medicationRepository.checkIfLoaded(code, new Integer[]{EStatus.LOADING.getCode(), EStatus.LOADED.getCode()}) != 1)
            throw new DroneAlreadyBusyException("Medication cannot be remove, it is being delivering");
        try {
            medicationRepository.delete(medicationRepository.findByCode(code).orElseThrow(() -> new ResourceNotFoundException("Medication with code not exists")));
            log.info("A medication with code #{} is deleted...", code);
        } catch (DataAccessException ex) {
            throw new BusinessErrorException("Medication identified by code - " + ex.getMostSpecificCause().getMessage());
        }
    }

    @Override
    public MedicationDto updatedImage(String code, MultipartFile multiPartFile) throws ResourceNotFoundException, InvalidRequestException {
        Medication medication = medicationRepository.findByCode(code).orElseThrow(() -> new ResourceNotFoundException("no medication found for id"));
        String filename = fileUploaderService.uploadFile(multiPartFile);
        medication.setImage(filename);
        log.info("image file of medication Code: #{} stored and renamed to {} successfully", code, filename);
        return medicationMapper.toDto(medicationRepository.save(medication));
    }

    @Override
    public boolean checkIfMedicationIsLoadedOrDelivered(String code) {
        return medicationRepository.checkIfLoadedOrIsInState(code) != 0;
    }

    @Override
    public Medication findByCode(String code) {
        return medicationRepository.findByCode(code).orElseThrow(() -> new ResourceNotFoundException("medication not found with code" + code));
    }

}
