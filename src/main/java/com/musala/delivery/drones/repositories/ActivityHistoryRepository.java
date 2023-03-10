package com.musala.delivery.drones.repositories;

import com.musala.delivery.drones.entities.ActivityHistory;
import com.musala.delivery.drones.enumerations.EStatus;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ActivityHistoryRepository extends JpaRepository<ActivityHistory, Long> {

    @Transactional
    @Query("UPDATE ActivityHistory h SET h.historyState = :state WHERE h.id = :id")
    void updateState(long id, EStatus state);
}
