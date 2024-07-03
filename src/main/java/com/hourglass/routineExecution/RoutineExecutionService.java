package com.hourglass.routineExecution;

import com.hourglass.exception.ResourceNotFoundException;
import com.hourglass.user.User;
import com.hourglass.user.UserService;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

@Service
public class RoutineExecutionService {

    private final RoutineExecutionRepository routineExecutionRepository;
    private final UserService userService;

    public RoutineExecutionService(RoutineExecutionRepository routineExecutionRepository, UserService userService) {
        this.routineExecutionRepository = routineExecutionRepository;
        this.userService = userService;
    }

    public void deleteRoutineExecution(Long routineExecutionId) {
        RoutineExecution routineExecution = routineExecutionRepository.findById(routineExecutionId)
                .orElseThrow(() -> new ResourceNotFoundException("RoutineExecution with id [%s] not found"
                        .formatted(routineExecutionId)));
        ensureUserOwnsRoutineExecution(routineExecution);
        routineExecutionRepository.deleteById(routineExecutionId);
    }

    private void ensureUserOwnsRoutineExecution(RoutineExecution routineExecution) {
        User currentUser = userService.getCurrentUser();
        if (!routineExecution.getRoutine().getUser().equals(currentUser)) {
            throw new AccessDeniedException("You are not authorized to delete this routine");
        }
    }

}
