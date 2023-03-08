package com.musala.delivery.drones.entities;

import com.musala.delivery.drones.enumerations.EModel;
import com.musala.delivery.drones.enumerations.EStatus;

import jakarta.annotation.Nonnull;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.validation.annotation.Validated;

@Data
@Builder
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
    @NotBlank()
    private String serialNumber;

    @Column(name = "BATTERY_CAPACITY")
    private Integer batteryCapacity;

    @Column(name = "STATE")
    @Nonnull
    @Enumerated(EnumType.ORDINAL)
    private EStatus state;

    @Max(500)
    @Min(0)
    @Column(name = "WEIGHT_LIMIT",  precision = 3)
    private Float weightLimit;
    
    public Drone(EModel model, String serialNumber) {
    	this.model = model;
    	this.serialNumber = serialNumber;
    	
    }
}
