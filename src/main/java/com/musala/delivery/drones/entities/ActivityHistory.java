package com.musala.delivery.drones.entities;

import com.musala.delivery.drones.enumerations.EStatus;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EqualsAndHashCode
@Table(name = "MS_DL_DRONE_HISTORY")
@SequenceGenerator(name = "SeqHistory", sequenceName = "SeqHistory", initialValue = 1, allocationSize = 1)
public class ActivityHistory {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SeqHistory")
    private Long id;

    @Column(name = "ORIGIN_LOCATION")
    private String originLocation;

    @Column(name = "DESTINATION_LOCATION")
    private String destinationLocation;

    @Enumerated(EnumType.STRING)
    @Column(name = "TRIP_STATE")
    private EStatus historyState;

    @Column(name = "STARTED_AT")
    @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm")
    private LocalDateTime startedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DRONE_ID", referencedColumnName = "id")
    private Drone drone;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEDICATION_ID", referencedColumnName = "id")
    private Medication medication;
}
