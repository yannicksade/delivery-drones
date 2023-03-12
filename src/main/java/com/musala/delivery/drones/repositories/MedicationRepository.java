package com.musala.delivery.drones.repositories;

import com.musala.delivery.drones.entities.Medication;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.musala.delivery.drones.enumerations.EStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicationRepository extends JpaRepository<Medication, Long> {

    Optional<Medication> findByCode(String mdedication);

    @Query(value = "SELECT COUNT(*) FROM MS_DL_MEDICATION m LEFT JOIN MS_DL_DRONE d ON d.ID = m.DRONE_ID WHERE m.CODE = :code AND (m.DRONE_ID IS NULL OR d.STATE IN :states)", nativeQuery = true)
    Integer checkIfLoaded(String code, Integer[] states);

    @Query(value = "SELECT COUNT(*) FROM MS_DL_MEDICATION m LEFT JOIN MS_DL_DRONE d ON d.ID = m.DRONE_ID LEFT JOIN MS_DL_DRONE_HISTORY h ON h.MEDICATION_ID = m.ID WHERE m.CODE = :code AND (m.DRONE_ID IS NOT NULL)", nativeQuery = true)
    Integer checkIfLoadedOrIsInState(String code);

    @Query("SELECT DISTINCT m1 FROM Medication m1 JOIN Drone d ON m1 IN (SELECT m2 FROM d.medications m2)  WHERE d.serialNumber = :serial")
    List<Medication> findAllByDrone(String serial);

}
