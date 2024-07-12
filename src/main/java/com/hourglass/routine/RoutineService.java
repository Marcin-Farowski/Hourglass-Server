package com.hourglass.routine;

import com.hourglass.exception.ResourceNotFoundException;
import com.hourglass.user.User;
import com.hourglass.user.UserService;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoutineService {

    private final UserService userService;
    private final RoutineRepository routineRepository;

    public RoutineService(UserService userService, RoutineRepository routineRepository) {
        this.userService = userService;
        this.routineRepository = routineRepository;
    }

    public List<Routine> getRoutinesByUser() {
        User currentUser = userService.getCurrentUser();
        return routineRepository.findRoutinesByUser(currentUser);
    }

    public Routine createRoutine(RoutineCreationRequest routineCreationRequest) {
        User currentUser = userService.getCurrentUser();
        Routine routine = new Routine(routineCreationRequest.name(), routineCreationRequest.startDateTime(),
                routineCreationRequest.renewalInterval(), currentUser);
        return routineRepository.save(routine);
    }

    public void deleteRoutine(Long routineId) {
        Routine routine = routineRepository.findById(routineId)
                .orElseThrow(() -> new ResourceNotFoundException("Routine with id [%s] not found".formatted(routineId)));
        ensureUserOwnsRoutine(routine);
        routineRepository.deleteById(routineId);
    }

    public Routine executeRoutine(Long routineId) {
        Routine routine = routineRepository.findById(routineId)
                .orElseThrow(() -> new ResourceNotFoundException("Routine with id [%s] not found".formatted(routineId)));
        ensureUserOwnsRoutine(routine);
        routine.addRoutineExecution();
        return routineRepository.save(routine);
    }

    private void ensureUserOwnsRoutine(Routine routine) {
        User currentUser = userService.getCurrentUser();
        if (!routine.getUser().equals(currentUser)) {
            throw new AccessDeniedException("You are not authorized to delete this routine");
        }
    }
}
