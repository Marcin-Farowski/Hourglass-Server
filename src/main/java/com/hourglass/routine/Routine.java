package com.hourglass.routine;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Routine {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "routine_seq")
    @SequenceGenerator(name = "routine_seq", sequenceName = "routine_sequence", allocationSize = 1)
    private Integer id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private LocalDateTime startDateTime;

    @Embedded
    @Column(nullable = false)
    private TimeInterval renewalInterval;

    public Routine(String name, LocalDateTime startDateTime, TimeInterval renewalInterval) {
        this.name = name;
        this.startDateTime = startDateTime;
        this.renewalInterval = renewalInterval;
    }
}
