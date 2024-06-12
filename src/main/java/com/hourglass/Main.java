package com.hourglass;

import com.hourglass.routine.Routine;
import com.hourglass.routine.RoutineRepository;
import com.hourglass.routine.TimeInterval;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootApplication
public class Main {

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(RoutineRepository routineRepository) {
        return args -> {
            Routine routine1 = new Routine(null, "Morning Exercise",
                    LocalDateTime.of(2024, 6, 12, 6, 0),
                    new TimeInterval(0, 0, 1, 0, 0, 0));
            Routine routine2 = new Routine(null, "Lunch Break",
                    LocalDateTime.of(2024, 6, 12, 12, 30),
                    new TimeInterval(0, 0, 0, 1, 0, 0));
            Routine routine3 = new Routine(null, "Afternoon Nap",
                    LocalDateTime.of(2024, 6, 12, 15, 0),
                    new TimeInterval(0, 30, 0, 0, 0, 0));
            Routine routine4 = new Routine(null, "Evening Jog",
                    LocalDateTime.of(2024, 6, 12, 18, 0),
                    new TimeInterval(0, 0, 1, 0, 0, 0));
            Routine routine5 = new Routine(null, "Reading Time",
                    LocalDateTime.of(2024, 6, 12, 21, 0),
                    new TimeInterval(0, 0, 2, 0, 0, 0));

            routineRepository.saveAll(List.of(routine1, routine2, routine3, routine4, routine5));
        };
    }

}
