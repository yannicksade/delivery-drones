package com.musala.delivery.drones.repositories;

import com.musala.delivery.drones.entities.Medication;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicationRepository extends JpaRepository<Medication, Long> {
	
	Optional<Medication> findByCode(String mdedication);
	
	Optional<Medication> findByName(String name);
}
