package com.musala.delivery.drones.entities;

import com.musala.delivery.drones.constraints.CodeFieldConstraint;
import com.musala.delivery.drones.constraints.NameFieldConstraint;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import org.springframework.validation.annotation.Validated;

import java.util.ArrayList;
import java.util.List;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EqualsAndHashCode
@Validated
@Table(name = "MS_DL_MEDICATION", uniqueConstraints = @UniqueConstraint(columnNames = "CODE", name = "code"))
//@SequenceGenerator(name = "SeqMedication", sequenceName = "SeqMedication", initialValue = 1, allocationSize = 1)
public class Medication {
    @Id
    @Column(name = "ID")
    // @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SeqMedication")
    private Long id;

    //@CodeFieldConstraint(message = "Code must only have UPPERCASE letters, underscores and numbers")
    @Column(name = "CODE")
    @NotBlank
    private String code;

    //@NameFieldConstraint(message = "Name must only have letters, numbers, hyphen and/or underscore")
    @Column(name = "NAME")
    @NotBlank
    private String name;

    @Max(500)
    @Min(0)
    @Column(name = "WEIGHT",  precision = 3)
    private Float weight;

    @Column(name = "IMAGE_FILENAME")
    private String image;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEDICATION_ID")
    private List<ActivityHistory> histories = new ArrayList<>();

    public Medication(String name) {
    	this.name = name;
    	
    }
}
