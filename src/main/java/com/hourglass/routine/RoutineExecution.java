package com.hourglass.routine;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoutineExecution {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Routine routine;

    private LocalDateTime executionTime;


    public RoutineExecution(Routine routine, LocalDateTime executionTime, boolean wasSuccessful, String notes) {
        this.routine = routine;
        this.executionTime = executionTime;
    }
}
