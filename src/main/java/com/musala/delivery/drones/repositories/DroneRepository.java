package com.musala.delivery.drones.repositories;

import com.musala.delivery.drones.entities.Drone;
import com.musala.delivery.drones.enumerations.EModel;
import com.musala.delivery.drones.enumerations.EStatus;

import jakarta.transaction.Transactional;

import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DroneRepository extends JpaRepository<Drone, Long> {
	
	Optional<Drone> findBySerialNumber(String serialNumber);
	
	Set<Drone> findByState(EStatus status);
	
	Optional<Drone> findByModelAndSerialNumber(EModel model, String serialNumber);
	
	@Transactional
	@Modifying
	@Query("UPDATE drone dn SET dn.state = :state where dn.id = :id")
	void updateDroneState(long id, EStatus state);

	//boolean exists(Example<Medication> example);
}
