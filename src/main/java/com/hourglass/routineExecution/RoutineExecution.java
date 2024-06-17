package com.hourglass.routineExecution;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hourglass.routine.Routine;
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
    @JsonIgnore
    private Routine routine;

    private LocalDateTime executionTime;


    public RoutineExecution(Routine routine, LocalDateTime executionTime) {
        this.routine = routine;
        this.executionTime = executionTime;
    }
}
