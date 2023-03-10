package com.musala.delivery.drones.services.impl;

import com.musala.delivery.drones.dto.HistoryDto;
import com.musala.delivery.drones.dto.HistoryRequestDto;
import com.musala.delivery.drones.entities.ActivityHistory;
import com.musala.delivery.drones.enumerations.EStatus;
import com.musala.delivery.drones.services.exceptions.ResourceNotFoundException;
import com.musala.delivery.drones.mappers.HistoryMapper;
import com.musala.delivery.drones.repositories.ActivityHistoryRepository;
import com.musala.delivery.drones.services.ActivityHistoryService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.chrono.ChronoPeriod;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class ActivityHistoryImpl implements ActivityHistoryService {

    private final HistoryMapper historyMapper;

    private final ActivityHistoryRepository historyRepository;

    @PersistenceContext
    private final EntityManager entityManager;

    @Override
    public List<HistoryDto> getHistories(HistoryRequestDto requestDto) {
        return Optional.ofNullable(entityManager.createQuery(getCriteria(requestDto, null, -1)).getResultList()).orElse(new ArrayList<>())
                .stream().map(historyMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public List<HistoryDto> getHistoriesByDrone(long droneID, HistoryRequestDto requestDto) {
        return Optional.ofNullable(entityManager.createQuery(getCriteria(requestDto, "drone", droneID)).getResultList()).orElse(new ArrayList<>())
                .stream().map(historyMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public List<HistoryDto> getHistoriesByMedication(long medicationID, HistoryRequestDto requestDto) {
        return Optional.ofNullable(entityManager.createQuery(getCriteria(requestDto, "medication", medicationID)).getResultList()).orElse(new ArrayList<>())
                .stream().map(historyMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public void updateActivityState(long id, EStatus state) {
        historyRepository.updateState(id, state);
    }

    @Override
    public ActivityHistory createHistory(ActivityHistory activityHistory) {
        return historyRepository.save(activityHistory);
    }

    @Override
    public HistoryDto getHistoryDetails(long id) {
        return historyMapper.toDto(historyRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("no history details with that ID")));
    }

    private CriteriaQuery<ActivityHistory> getCriteria(HistoryRequestDto requestDto, String field, long id) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<ActivityHistory> query = cb.createQuery(ActivityHistory.class);
        List<Predicate> predicates = new ArrayList<>();
        Root<ActivityHistory> history = query.from(ActivityHistory.class);
        if ("drone".equals(field)) {
            Join<Object, Object> root = history.join("drone", JoinType.INNER);
            predicates.add(cb.equal(root.get("id"), id));
        }
        if ("medication".equals(field)) {
            Join<Object, Object> drone = history.join("medication", JoinType.INNER);
            predicates.add(cb.equal(drone.get("id"), id));
        }
        LocalDateTime start = LocalDateTime.now().minusDays(1);
        LocalDateTime end = LocalDateTime.now();
        if (requestDto.getStartedAt() != null)
            start = requestDto.getStartedAt();
        predicates.add(cb.between(history.get("startedAt"), start, end));
        if (requestDto.getHistoryState() != null)
            predicates.add(cb.equal(history.get("historyState"), requestDto.getHistoryState()));
        if (requestDto.getDestinationLocation() != null)
            predicates.add(cb.like(history.get("destinationLocation"), requestDto.getDestinationLocation()));
        if (requestDto.getOriginLocation() != null)
            predicates.add(cb.like(history.get("originLocation"), requestDto.getOriginLocation()));
        int nb = predicates.size();
        return query.select(history)
                .where(cb.and(predicates.toArray(new Predicate[nb])));
    }

}
