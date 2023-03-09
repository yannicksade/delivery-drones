package com.musala.delivery.drones.services.impl;

import com.musala.delivery.drones.dto.HistoryDto;
import com.musala.delivery.drones.dto.HistoryRequestDto;
import com.musala.delivery.drones.entities.ActivityHistory;
import com.musala.delivery.drones.entities.Drone;
import com.musala.delivery.drones.entities.Medication;
import com.musala.delivery.drones.mappers.HistoryMapper;
import com.musala.delivery.drones.repositories.ActivityHistoryRepository;
import com.musala.delivery.drones.services.ActivityHistoryService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class ActivityHistoryImpl implements ActivityHistoryService {

    private final HistoryMapper historyMapper;

    @PersistenceContext
    private final EntityManager entityManager;

    @Override
    public List<HistoryDto> getHistories(HistoryRequestDto requestDto) {
        return  entityManager.createQuery(getCriteria(requestDto, null, -1)).getResultList()
                .stream().map(historyMapper::toDto).collect(Collectors.toList());
    }
    @Override
    public List<HistoryDto> getHistoriesByDrone(long droneID, HistoryRequestDto requestDto) {
        return  entityManager.createQuery(getCriteria(requestDto, "drone", droneID)).getResultList()
        .stream().map(historyMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public List<HistoryDto> getHistoriesByMedication(long medicationID, HistoryRequestDto requestDto) {
        return  entityManager.createQuery(getCriteria(requestDto, "medication", medicationID)).getResultList()
                .stream().map(historyMapper::toDto).collect(Collectors.toList());
    }

    private CriteriaQuery<ActivityHistory> getCriteria(HistoryRequestDto requestDto, String field, long id) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<ActivityHistory> query = cb.createQuery(ActivityHistory.class);
        List<Predicate> predicates = new ArrayList<>();
        Root<ActivityHistory> history = query.from(ActivityHistory.class);
        if(field == "drone") {
            Join<Object, Object> drone = history.join(Drone.class.getName(), JoinType.INNER);
            predicates.add(cb.equal(drone.get("id"), id));
        }
        if(field == "medication") {
            Join<Object, Object> drone = history.join(Medication.class.getName(), JoinType.INNER);
            predicates.add(cb.equal(drone.get("id"), id));
        }
        String  end, start = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        end =  LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"));
        if(requestDto.getStartedAt() != null)
            start = requestDto.getStartedAt().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"));;
        predicates.add(cb.between(history.get("startedAt"), start, end));
        if(requestDto.getHistoryState() != null)
            predicates.add(cb.equal(history.get("historyState"), requestDto.getHistoryState()));
        if(requestDto.getDestinationLocation() != null)
            predicates.add(cb.like(history.get("destinationLocation"), requestDto.getDestinationLocation()));
        if(requestDto.getOriginLocation() != null)
            predicates.add(cb.like(history.get("originLocation"), requestDto.getOriginLocation()));

        return query.select(history)
                .where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
    }

}
