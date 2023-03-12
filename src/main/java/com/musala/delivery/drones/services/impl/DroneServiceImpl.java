package com.musala.delivery.drones.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

import com.musala.delivery.drones.entities.dto.DroneDto;
import com.musala.delivery.drones.entities.dto.DroneRequestDto;
import com.musala.delivery.drones.exceptions.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.musala.delivery.drones.entities.Drone;
import com.musala.delivery.drones.enumerations.EStatus;
import com.musala.delivery.drones.mappers.DroneMapper;
import com.musala.delivery.drones.repositories.DroneRepository;
import com.musala.delivery.drones.services.DroneService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class DroneServiceImpl implements DroneService {

    private final DroneRepository droneRepository;

    private final DroneMapper droneMapper;

    @PersistenceContext
    private final EntityManager entityManager;

    @Override
    public Drone findBySerialNumber(String serialNumber) throws ResourceNotFoundException {
        return droneRepository.findBySerialNumber(serialNumber).orElseThrow(() -> new ResourceNotFoundException("No drone found for the serialNumber or any Key"));
    }

    @Override
    public List<DroneDto> getAllAvailableDrones() {
        return droneRepository.findByState(EStatus.IDLE).stream().map(droneMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public List<Drone> findAllDrones() {
        return droneRepository.findAll();
    }

    private CriteriaQuery<Drone> getCriteria(DroneRequestDto requestDto) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Drone> query = cb.createQuery(Drone.class);
        List<Predicate> predicates = new ArrayList<>();
        Root<Drone> drone = query.from(Drone.class);
        if (requestDto.getModel() != null)
            predicates.add(cb.equal(drone.get("model"), requestDto.getModel()));
        if (requestDto.getSerialNumber() != null)
            predicates.add(cb.like(drone.get("serialNumber"), requestDto.getSerialNumber()));
        if (requestDto.getWeightLimit() != null)
            predicates.add(cb.equal(drone.get("weightLimit"), requestDto.getWeightLimit()));
        int nb = predicates.size();
        return query.select(drone)
                .where(cb.and(predicates.toArray(new Predicate[nb])));
    }

    @Override
    public DroneDto registerDrone(DroneRequestDto request)
            throws InvalidRequestException, DroneAlreadyRegisteredException, BusinessErrorException {
        Optional<Drone> drone = droneRepository.findByModelAndSerialNumber(request.getModel(),
                request.getSerialNumber());
        if (drone.isPresent()) {
            throw new DroneAlreadyRegisteredException("the provided drone item is already registered");
        }
        DroneDto droneDto = validateDrone(request);
        log.info("A drone with  serial number #{} and model {} is saving ", request.getSerialNumber(), request.getModel());
        try {
            droneDto = droneMapper.toDto(droneRepository.save(
                    Drone.builder()
                            .id(new Random().nextLong(10, 100))
                            .batteryLevel(100)
                            .state(EStatus.IDLE)
                            .model(droneDto.getModel())
                            .weightLimit(droneDto.getWeightLimit())
                            .serialNumber(droneDto.getSerialNumber())
                            .build()
            ));
        } catch (DataAccessException ex) {
            throw new BusinessErrorException("Drone identified by serialNumber may exists already - " + ex.getMostSpecificCause().getMessage());
        }
        return droneDto;
    }

    @Override
    public DroneDto getDroneBySerialNumber(String serialNumber) throws ResourceNotFoundException {
        return droneMapper.toDto(droneRepository.findBySerialNumber(serialNumber).orElseThrow(
                () -> new ResourceNotFoundException("No drone found with the serial number: " + serialNumber)));

    }

    @Override
    public DroneDto validateDrone(DroneRequestDto request) throws InvalidRequestException {
        if (request.getModel().name().isBlank()) {
            throw new InvalidRequestException("Drone Model is required");
        }
        if (request.getSerialNumber().isEmpty()) {
            throw new InvalidRequestException("Drone serial number is required");
        }
        if (request.getWeightLimit() < 0 || request.getWeightLimit() > 500) {
            throw new InvalidRequestException(
                    "A drone total weight must be greater than 0 and lesser than 500 grammes");
        }
        return droneMapper.toDto(droneMapper.toEntity(request));
    }

    @Override
    public DroneDto updateDrone(DroneRequestDto request) throws DroneAlreadyBusyException, ResourceNotFoundException, BusinessErrorException {
        validateDrone(request);
        Drone drone = droneRepository.findBySerialNumber(request.getSerialNumber()).orElseThrow(() -> new ResourceNotFoundException("No drone with that serial nomber"));
        if (!drone.getState().equals(EStatus.IDLE)) throw new DroneAlreadyBusyException("Drone is Busy cannot update");
        if (!drone.getModel().equals(request.getModel()))
            drone.setModel(request.getModel());
        if (!drone.getWeightLimit().equals(request.getWeightLimit()))
            drone.setWeightLimit(request.getWeightLimit());
        try {
            drone = droneRepository.save(drone);
        } catch (DataAccessException ex) {
            throw new BusinessErrorException("Drone identified by serialNumber - " + ex.getMostSpecificCause().getMessage());
        }
        log.info("A drone with serial number #{} is updated...", drone.getSerialNumber());
        return droneMapper.toDto(drone);
    }

    @Override
    public void removeDrone(String serialNumber) throws DroneAlreadyBusyException, BusinessErrorException {
        //remove only when not busy
        try {
            if (droneRepository.checkState(serialNumber, EStatus.IDLE) == 0)
                throw new DroneAlreadyBusyException("Drone is Busy cannot be deleted ");

            droneRepository.delete(droneRepository.findBySerialNumber(serialNumber).orElseThrow(() -> new ResourceNotFoundException("Drone with serial Number not Exists")));
            log.info("A drone with serial number #{} is deleted...", serialNumber);
        } catch (DataAccessException ex) {
            throw new BusinessErrorException("Drone identified by serialNumber - " + ex.getMostSpecificCause().getMessage());
        }
    }

    @Override
    public List<DroneDto> listAllDrones(DroneRequestDto request) {
        return Optional.ofNullable(entityManager.createQuery(getCriteria(request)).getResultList()).orElse(new ArrayList<>())
                .stream().map(droneMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public Drone save(Drone drone) {
        return droneRepository.save(drone);
    }

    @Override
    public List<Drone> getWorkingDrones() {
        return droneRepository.findWorkingDrones(EStatus.IDLE);
    }

    @Override
    public float checkDroneBatteryLevelById(String serialNumber) throws ResourceNotFoundException {
        return droneRepository.findBySerialNumber(serialNumber).map(Drone::getBatteryLevel)
                .orElseThrow(() -> new ResourceNotFoundException("No drone found with the SN : " + serialNumber));
    }

    @Override
    public void updateDroneStateById(long id, EStatus state) {
        //log.info("A drone with ID {} is changed state to {}", id, state);
        droneRepository.updateDroneState(id, state);
    }
}
