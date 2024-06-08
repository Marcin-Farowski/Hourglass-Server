package com.hourglass.routine;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Routine {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "routine_seq")
    @SequenceGenerator(name = "routine_seq", sequenceName = "routine_sequence", allocationSize = 1)
    private int id;
    @Column(nullable = false)
    private String name;
    private LocalTime startTime;
    private Integer renewalInterval;

}
