package com.hourglass.routine;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hourglass.routineExecution.RoutineExecution;
import com.hourglass.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Routine {

    @Id
    @SequenceGenerator(
            name = "routine_id_seq",
            sequenceName = "routine_id_seq",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "routine_id_seq")
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private LocalDateTime startDateTime;

    @Embedded
    @Column(nullable = false)
    private TimeInterval renewalInterval;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    private User user;

    @OneToMany(mappedBy = "routine", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RoutineExecution> routineExecutions = new ArrayList<>();

    public Routine(String name, LocalDateTime startDateTime, TimeInterval renewalInterval, User user) {
        this.name = name;
        this.startDateTime = startDateTime;
        this.renewalInterval = renewalInterval;
        this.user = user;
    }

    public void addRoutineExecution() {
        routineExecutions.add(new RoutineExecution(this, LocalDateTime.now()));
    }
}
