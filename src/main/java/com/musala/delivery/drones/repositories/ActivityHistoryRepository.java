package com.musala.delivery.drones.repositories;

import com.musala.delivery.drones.entities.ActivityHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActivityHistoryRepository extends JpaRepository<ActivityHistory, Long> {
}
