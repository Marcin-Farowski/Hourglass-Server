package com.hourglass.routine;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hourglass.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

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

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    private User user;

    public Routine(String name, LocalDateTime startDateTime, TimeInterval renewalInterval, User user) {
        this.name = name;
        this.startDateTime = startDateTime;
        this.renewalInterval = renewalInterval;
        this.user = user;
    }
}
