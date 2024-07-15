package com.hourglass.routine;

import com.hourglass.exception.RequestValidationException;
import com.hourglass.exception.ResourceNotFoundException;
import com.hourglass.user.Gender;
import com.hourglass.user.Role;
import com.hourglass.user.User;
import com.hourglass.user.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RoutineServiceTest {

    @Mock
    private UserService userService;
    @Mock
    private RoutineRepository routineRepository;
    private RoutineService underTest;
    @Captor
    private ArgumentCaptor<Routine> routineCaptor;

    @BeforeEach
    void setUp() {
        underTest = new RoutineService(userService, routineRepository);
    }

    @Test
    void getRoutinesByUser() {
        // Given
        User user = mock(User.class);

        List<Routine> routines = new ArrayList<>();
        routines.add(new Routine("Gym Workout", LocalDateTime.now().minusDays(14).withHour(6).withMinute(30), new TimeInterval(0, 0, 1, 0, 0, 0), user));
        routines.add(new Routine("Team Standup", LocalDateTime.now().minusDays(14).withHour(9).withMinute(30), new TimeInterval(0, 0, 1, 0, 0, 0), user));
        routines.add(new Routine("Coding Session", LocalDateTime.now().minusDays(14).withHour(14).withMinute(0), new TimeInterval(0, 0, 1, 0, 0, 0), user));

        // When
        when(userService.getCurrentUser()).thenReturn(user);
        when(routineRepository.findRoutinesByUser(user)).thenReturn(routines);

        List <Routine> actual = underTest.getRoutinesByUser();

        // Then
        assertThat(actual).isEqualTo(routines);
        verify(userService).getCurrentUser();
        verify(routineRepository).findRoutinesByUser(user);
    }

    @Test
    void createRoutine() {
        // Given
        User user = mock(User.class);
        RoutineCreationRequest request = new RoutineCreationRequest(
                "New Routine",
                LocalDateTime.now(),
                new TimeInterval(0, 0, 1, 0, 0, 0)
        );

        // When
        when(userService.getCurrentUser()).thenReturn(user);
        when(routineRepository.save(any(Routine.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Routine actual = underTest.createRoutine(request);

        // Then
        verify(userService).getCurrentUser();
        verify(routineRepository).save(routineCaptor.capture());

        Routine capturedRoutine = routineCaptor.getValue();
        assertThat(capturedRoutine.getName()).isEqualTo(request.name());
        assertThat(capturedRoutine.getStartDateTime()).isEqualTo(request.startDateTime());
        assertThat(capturedRoutine.getRenewalInterval()).isEqualTo(request.renewalInterval());
        assertThat(capturedRoutine.getUser()).isEqualTo(user);

        assertThat(actual).isEqualTo(capturedRoutine);
    }


    @Test
    void deleteRoutine() {
        // Given
        User user = mock(User.class);

        Routine routine = new Routine(
                "Test Routine",
                LocalDateTime.now(),
                new TimeInterval(0, 0, 1, 0, 0, 0),
                user
        );

        Long routineId = 1L;

        // When
        when(routineRepository.findById(routineId)).thenReturn(Optional.of(routine));
        when(userService.getCurrentUser()).thenReturn(user);

        underTest.deleteRoutine(routineId);

        // Then
        verify(routineRepository).findById(routineId);
        verify(routineRepository).deleteById(routineId);
        verify(userService).getCurrentUser();
    }

    @Test
    void deleteRoutine_shouldThrowResourceNotFoundExceptionWhenRoutineNotFound() {
        // Given
        Long routineId = 1L;

        // When
        when(routineRepository.findById(routineId)).thenReturn(Optional.empty());

        // Then
        assertThatThrownBy(() -> underTest.deleteRoutine(routineId))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Routine with id [%s] not found".formatted(routineId));
    }

    @Test
    void deleteRoutine_shouldThrowAccessDeniedExceptionWhenUserDoesNotOwnRoutine() {
        // Given
        User owner = mock(User.class);
        User currentUser = mock(User.class);
        Routine routine = new Routine(
                "Test Routine",
                LocalDateTime.now(),
                new TimeInterval(0, 0, 1, 0, 0, 0),
                owner
        );
        Long routineId = 1L;

        // When
        when(routineRepository.findById(routineId)).thenReturn(Optional.of(routine));
        when(userService.getCurrentUser()).thenReturn(currentUser);

        // Then
        assertThatThrownBy(() -> underTest.deleteRoutine(routineId))
                .isInstanceOf(AccessDeniedException.class)
                .hasMessage("You are not authorized to access this routine");
    }

    @Test
    void updateRoutine() {
        // Given
        User user = mock(User.class);

        Routine routine = new Routine(
                "Test Routine",
                LocalDateTime.now(),
                new TimeInterval(0, 0, 1, 0, 0, 0),
                user
        );

        RoutineUpdateRequest request = new RoutineUpdateRequest(
                "New Test Routine Name",
                new TimeInterval(0, 0, 2, 0, 0, 0)
        );

        Long routineId = 1L;

        // When
        when(routineRepository.findById(routineId)).thenReturn(Optional.of(routine));
        when(userService.getCurrentUser()).thenReturn(user);
        when(routineRepository.save(routine)).thenReturn(new Routine(request.name(), routine.getStartDateTime(), request.renewalInterval(), user));

        Routine actual = underTest.updateRoutine(routineId, request);

        // Then
        assertThat(actual.getName()).isEqualTo(request.name());
        assertThat(actual.getRenewalInterval()).isEqualTo(request.renewalInterval());
        verify(routineRepository).save(routine);
    }

    @Test
    void updateRoutine_shouldUpdateOnlyRoutineName() {
        // Given
        User user = mock(User.class);
        Routine routine = new Routine(
                "Old Name",
                LocalDateTime.now(),
                new TimeInterval(0, 0, 1, 0, 0, 0),
                user
        );

        RoutineUpdateRequest request = new RoutineUpdateRequest(
                "New Name",
                null
        );

        Long routineId = 1L;

        // When
        when(routineRepository.findById(routineId)).thenReturn(Optional.of(routine));
        when(userService.getCurrentUser()).thenReturn(user);
        when(routineRepository.save(routine)).thenReturn(new Routine(request.name(), routine.getStartDateTime(), routine.getRenewalInterval(), user));

        Routine actual = underTest.updateRoutine(routineId, request);

        // Then
        assertThat(actual.getName()).isEqualTo(request.name());
        assertThat(actual.getRenewalInterval()).isEqualTo(routine.getRenewalInterval());
        verify(routineRepository).save(routine);
    }

    @Test
    void updateRoutine_shouldUpdateOnlyRenewalInterval() {
        // Given
        User user = mock(User.class);
        Routine routine = new Routine(
                "Test Routine",
                LocalDateTime.now(),
                new TimeInterval(0, 0, 1, 0, 0, 0),
                user
        );

        RoutineUpdateRequest request = new RoutineUpdateRequest(
                null,
                new TimeInterval(0, 0, 2, 0, 0, 0)
        );

        Long routineId = 1L;

        // When
        when(routineRepository.findById(routineId)).thenReturn(Optional.of(routine));
        when(userService.getCurrentUser()).thenReturn(user);
        when(routineRepository.save(routine)).thenReturn(new Routine(routine.getName(), routine.getStartDateTime(), request.renewalInterval(), user));

        Routine actual = underTest.updateRoutine(routineId, request);

        // Then
        assertThat(actual.getRenewalInterval()).isEqualTo(request.renewalInterval());
        assertThat(actual.getName()).isEqualTo(routine.getName());
        verify(routineRepository).save(routine);
    }

    @Test
    void updateRoutine_shouldThrowResourceNotFoundExceptionWhenRoutineNotFound() {
        // Given
        Long routineId = 1L;
        RoutineUpdateRequest updateRequest = mock(RoutineUpdateRequest.class);

        // When
        when(routineRepository.findById(routineId)).thenReturn(Optional.empty());

        // Then
        assertThatThrownBy(() -> underTest.updateRoutine(routineId, updateRequest))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Routine with id [%s] not found".formatted(routineId));
    }

    @Test
    void updateRoutine_shouldThrowAccessDeniedExceptionWhenUserDoesNotOwnRoutine() {
        // Given
        User owner = mock(User.class);
        User currentUser = mock(User.class);
        Routine routine = new Routine(
                "Test Routine",
                LocalDateTime.now(),
                new TimeInterval(0, 0, 1, 0, 0, 0),
                owner
        );
        RoutineUpdateRequest updateRequest = mock(RoutineUpdateRequest.class);
        Long routineId = 1L;

        // When
        when(routineRepository.findById(routineId)).thenReturn(Optional.of(routine));
        when(userService.getCurrentUser()).thenReturn(currentUser);

        // Then
        assertThatThrownBy(() -> underTest.updateRoutine(routineId, updateRequest))
                .isInstanceOf(AccessDeniedException.class)
                .hasMessage("You are not authorized to access this routine");
    }

    @Test
    void updateRoutine_shouldThrowRequestValidationExceptionWhenNoChanges() {
        // Given
        User user = mock(User.class);
        Routine routine = new Routine(
                "Test Routine",
                LocalDateTime.now(),
                new TimeInterval(0, 0, 1, 0, 0, 0),
                user
        );

        RoutineUpdateRequest request = new RoutineUpdateRequest(
                "Test Routine",
                new TimeInterval(0, 0, 1, 0, 0, 0)
        );

        Long routineId = 1L;

        // When
        when(routineRepository.findById(routineId)).thenReturn(Optional.of(routine));
        when(userService.getCurrentUser()).thenReturn(user);

        // Then
        assertThatThrownBy(() -> underTest.updateRoutine(routineId, request))
                .isInstanceOf(RequestValidationException.class)
                .hasMessage("No data changes found");

        verify(routineRepository, never()).save(any(Routine.class));
    }

    @Test
    void executeRoutine() {
        // Given
        User user = mock(User.class);

        Routine routine = new Routine(
                "Test Routine",
                LocalDateTime.now(),
                new TimeInterval(0, 0, 1, 0, 0, 0),
                user
        );

        Long routineId = 1L;

        // When
        when(routineRepository.findById(routineId)).thenReturn(Optional.of(routine));
        when(userService.getCurrentUser()).thenReturn(user);
        routine.addRoutineExecution();
        when(routineRepository.save(routine)).thenReturn(routine);

        Routine actual = underTest.executeRoutine(routineId);

        // Then
        assertThat(actual).isEqualTo(routine);
        verify(routineRepository).findById(routineId);
        verify(userService).getCurrentUser();
        verify(routineRepository).save(routine);
    }

    @Test
    void executeRoutine_shouldThrowResourceNotFoundExceptionWhenRoutineNotFound() {
        // Given
        Long routineId = 1L;

        // When
        when(routineRepository.findById(routineId)).thenReturn(Optional.empty());

        // Then
        assertThatThrownBy(() -> underTest.executeRoutine(routineId))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Routine with id [%s] not found".formatted(routineId));
    }

    @Test
    void executeRoutine_shouldThrowAccessDeniedExceptionWhenUserDoesNotOwnRoutine() {
        // Given
        User owner = mock(User.class);
        User currentUser = mock(User.class);
        Routine routine = new Routine(
                "Test Routine",
                LocalDateTime.now(),
                new TimeInterval(0, 0, 1, 0, 0, 0),
                owner
        );
        Long routineId = 1L;

        // When
        when(routineRepository.findById(routineId)).thenReturn(Optional.of(routine));
        when(userService.getCurrentUser()).thenReturn(currentUser);

        // Then
        assertThatThrownBy(() -> underTest.executeRoutine(routineId))
                .isInstanceOf(AccessDeniedException.class)
                .hasMessage("You are not authorized to access this routine");
    }

}