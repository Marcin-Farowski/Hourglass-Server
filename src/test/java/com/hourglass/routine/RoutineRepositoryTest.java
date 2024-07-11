package com.hourglass.routine;

import com.hourglass.AbstractTestcontainers;
import com.hourglass.user.Gender;
import com.hourglass.user.Role;
import com.hourglass.user.User;
import com.hourglass.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class RoutineRepositoryTest extends AbstractTestcontainers {

    @Autowired
    private RoutineRepository underTest;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
    }

    @Test
    void findRoutinesByUser() {
        // Given
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String email = FAKER.internet().safeEmailAddress() + "-" + UUID.randomUUID();
        User user = User.builder()
                .firstName(FAKER.name().firstName())
                .lastName(FAKER.name().lastName())
                .dateOfBirth(FAKER.date().birthday().toInstant().atZone(ZoneId.systemDefault()).toLocalDate())
                .gender(Gender.MALE)
                .email(email)
                .password(passwordEncoder.encode("password"))
                .role(Role.USER)
                .build();

        List<Routine> routines = new ArrayList<>();
        routines.add(new Routine(FAKER.lorem().sentence(), LocalDateTime.now().minusDays(14).withHour(6).withMinute(0),
                new TimeInterval(0, 0, 1, 0, 0, 0), user));
        routines.add(new Routine(FAKER.lorem().sentence(), LocalDateTime.now().minusDays(14).withHour(18).withMinute(0),
                new TimeInterval(0, 0, 1, 0, 0, 0), user));
        routines.add(new Routine(FAKER.lorem().sentence(), LocalDateTime.now().minusDays(14).withHour(9).withMinute(0),
                new TimeInterval(0, 0, 0, 7, 0, 0), user));
        routines.add(new Routine(FAKER.lorem().sentence(), LocalDateTime.now().minusDays(14).withHour(17).withMinute(0),
                new TimeInterval(0, 0, 0, 7, 0, 0), user));
        routines.add(new Routine(FAKER.lorem().sentence(), LocalDateTime.now().minusDays(14).withHour(10).withMinute(0),
                new TimeInterval(0, 0, 0, 0, 1, 0), user));

        userRepository.save(user);
        underTest.saveAll(routines);

        // When
        var actual = underTest.findRoutinesByUser(user);

        //Then
        assertThat(actual).hasSize(5);
        assertThat(actual).containsExactlyInAnyOrderElementsOf(routines);
    }

    @Test
    void findRoutinesByUserWithNoRoutines() {
        // Given
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String email = FAKER.internet().safeEmailAddress() + "-" + UUID.randomUUID();
        User user = User.builder()
                .firstName(FAKER.name().firstName())
                .lastName(FAKER.name().lastName())
                .dateOfBirth(FAKER.date().birthday().toInstant().atZone(ZoneId.systemDefault()).toLocalDate())
                .gender(Gender.MALE)
                .email(email)
                .password(passwordEncoder.encode("password"))
                .role(Role.USER)
                .build();

        userRepository.save(user);

        // When
        var actual = underTest.findRoutinesByUser(user);

        // Then
        assertThat(actual).isEmpty();
    }
}