package com.hourglass.routine;

import com.hourglass.user.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class RoutineServiceTest {

    @Mock
    private UserService userService;
    @Mock
    private RoutineRepository routineRepository;
    private RoutineService underTest;

    @BeforeEach
    void setUp() {
        underTest = new RoutineService(userService, routineRepository);
    }

    @Test
    void getRoutinesByUser() {
        // Given

        // When

        // Then
    }

    @Test
    void createRoutine() {
        // Given

        // When

        // Then
    }

    @Test
    void deleteRoutine() {
        // Given

        // When

        // Then
    }

    @Test
    void executeRoutine() {
        // Given

        // When

        // Then
    }
}