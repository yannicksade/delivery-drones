package com.musala.delivery.drones.entities;

import com.musala.delivery.drones.constraints.CodeFieldConstraint;
import com.musala.delivery.drones.constraints.NameFieldConstraint;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.validation.annotation.Validated;


@Data
@Entity
@EqualsAndHashCode
@Validated
@Table(name = "MS_DL_MEDICATION", uniqueConstraints = @UniqueConstraint(columnNames = "CODE"))
@SequenceGenerator(name = "SeqMedication", sequenceName = "SeqMedication", initialValue = 1, allocationSize = 1)
public class Medication {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SeqMedication")
    private Long id;

    @CodeFieldConstraint
    @Column(name = "CODE")
    private String code;

    @NameFieldConstraint
    @Column(name = "NAME")
    private String name;

    @Max(500)
    @Min(0)
    @Column(name = "WEIGHT",  precision = 3, scale = 2)
    private Float weight;

    @Column(name = "IMAGE_FILENAME")
    private String image;
}
