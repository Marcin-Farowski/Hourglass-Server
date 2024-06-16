package com.hourglass;

import com.hourglass.routine.Routine;
import com.hourglass.routine.RoutineExecution;
import com.hourglass.routine.RoutineRepository;
import com.hourglass.routine.TimeInterval;
import com.hourglass.user.Role;
import com.hourglass.user.User;
import com.hourglass.user.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class Main {

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(UserRepository userRepository, RoutineRepository routineRepository) {
        return args -> {
            PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

            User user1 = User.builder()
                    .firstName("John")
                    .lastName("Doe")
                    .email("john.doe@example.com")
                    .password(passwordEncoder.encode("password"))
                    .role(Role.USER)
                    .build();

            User user2 = User.builder()
                    .firstName("Jane")
                    .lastName("Smith")
                    .email("jane.smith@example.com")
                    .password(passwordEncoder.encode("password"))
                    .role(Role.USER)
                    .build();

            User user3 = User.builder()
                    .firstName("Bob")
                    .lastName("Johnson")
                    .email("bob.johnson@example.com")
                    .password(passwordEncoder.encode("password"))
                    .role(Role.USER)
                    .build();

            userRepository.saveAll(List.of(user1, user2, user3));

            List<Routine> routines = new ArrayList<>();
            routines.addAll(createRoutinesForUser(user1));
            routines.addAll(createRoutinesForUser(user2));
            routines.addAll(createRoutinesForUser(user3));

            routineRepository.saveAll(routines);
        };
    }

    private List<Routine> createRoutinesForUser(User user) {
        List<Routine> routines = new ArrayList<>();

        if (user.getEmail().equals("john.doe@example.com")) {
            routines.add(new Routine("Morning Jogging", LocalDateTime.now().minusDays(14).withHour(6).withMinute(0), new TimeInterval(0, 0, 1, 0, 0, 0), user));
            routines.add(new Routine("Read a Book", LocalDateTime.now().minusDays(14).withHour(18).withMinute(0), new TimeInterval(0, 0, 1, 0, 0, 0), user));
            routines.add(new Routine("Team Meeting", LocalDateTime.now().minusDays(14).withHour(9).withMinute(0), new TimeInterval(0, 0, 0, 7, 0, 0), user));
            routines.add(new Routine("Grocery Shopping", LocalDateTime.now().minusDays(14).withHour(17).withMinute(0), new TimeInterval(0, 0, 0, 7, 0, 0), user));
            routines.add(new Routine("Monthly Report", LocalDateTime.now().minusDays(14).withHour(10).withMinute(0), new TimeInterval(0, 0, 0, 0, 1, 0), user));
        } else if (user.getEmail().equals("jane.smith@example.com")) {
            routines.add(new Routine("Yoga Class", LocalDateTime.now().minusDays(14).withHour(7).withMinute(0), new TimeInterval(0, 0, 0, 2, 0, 0), user));
            routines.add(new Routine("Project Meeting", LocalDateTime.now().minusDays(14).withHour(11).withMinute(0), new TimeInterval(0, 0, 0, 7, 0, 0), user));
            routines.add(new Routine("Lunch with Clients", LocalDateTime.now().minusDays(14).withHour(13).withMinute(0), new TimeInterval(0, 0, 0, 7, 0, 0), user));
            routines.add(new Routine("Piano Practice", LocalDateTime.now().minusDays(14).withHour(16).withMinute(0), new TimeInterval(0, 0, 1, 0, 0, 0), user));
            routines.add(new Routine("Dinner with Family", LocalDateTime.now().minusDays(14).withHour(19).withMinute(0), new TimeInterval(0, 0, 1, 0, 0, 0), user));
        } else if (user.getEmail().equals("bob.johnson@example.com")) {
            routines.add(new Routine("Gym Workout", LocalDateTime.now().minusDays(14).withHour(6).withMinute(30), new TimeInterval(0, 0, 1, 0, 0, 0), user));
            routines.add(new Routine("Team Standup", LocalDateTime.now().minusDays(14).withHour(9).withMinute(30), new TimeInterval(0, 0, 1, 0, 0, 0), user));
            routines.add(new Routine("Coding Session", LocalDateTime.now().minusDays(14).withHour(14).withMinute(0), new TimeInterval(0, 0, 1, 0, 0, 0), user));
            routines.add(new Routine("Weekly Sync", LocalDateTime.now().minusDays(14).withHour(10).withMinute(0), new TimeInterval(0, 0, 0, 7, 0, 0), user));
            routines.add(new Routine("Client Call", LocalDateTime.now().minusDays(14).withHour(15).withMinute(0), new TimeInterval(0, 0, 0, 7, 0, 0), user));
            routines.add(new Routine("Date Night", LocalDateTime.now().minusDays(14).withHour(20).withMinute(0), new TimeInterval(0, 0, 0, 7, 0, 0), user));
        }

        routines.forEach(routine -> {
            List<RoutineExecution> routineExecutions = createRoutineExecutions(routine, 3);
            routine.getRoutineExecutions().addAll(routineExecutions);
        });


        return routines;
    }

    private List<RoutineExecution> createRoutineExecutions(Routine routine, int numberOfExecutions) {
        List<RoutineExecution> routineExecutions = new ArrayList<>();
        LocalDateTime executionTime = routine.getStartDateTime().plusDays(1);
        for (int i = 0; i < numberOfExecutions; i++) {
            routineExecutions.add(new RoutineExecution(routine, executionTime.plusDays(i)));
        }
        return routineExecutions;
    }

}
