package com.musala.delivery.drones.entities;

import com.musala.delivery.drones.enumerations.EModel;
import com.musala.delivery.drones.enumerations.EStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.validation.annotation.Validated;

import java.util.HashSet;
import java.util.Set;

@Data
@ToString
@Entity
@EqualsAndHashCode
@Validated
@Table(name = "MS_DL_DRONE", uniqueConstraints = @UniqueConstraint(columnNames = "SERIAL_NUMBER"))
@SequenceGenerator(name = "SeqDrone", sequenceName = "SeqDrone", initialValue = 1, allocationSize = 1)
public class Drone {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SeqDrone")
    private Long id;

    @Column(name = "MODEL")
    @Enumerated(EnumType.ORDINAL)
    private EModel model;

    @Column(name = "SERIAL_NUMBER", length = 100)
    private String serialNumber;

    @Column(name = "BATTERY_CAPACITY")
    private Integer batteryCapacity;

    @Column(name = "STATE")
    @Enumerated(EnumType.ORDINAL)
    private EStatus state;

    @Max(500)
    @Min(0)
    @Column(name = "WEIGHT_LIMIT",  precision = 3, scale = 2)
    private Float weightLimit;

    @OneToMany(fetch = FetchType.LAZY)
    private Set<Medication> medications = new HashSet<>();
}
