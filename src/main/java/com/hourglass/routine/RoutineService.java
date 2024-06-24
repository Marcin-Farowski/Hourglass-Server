package com.hourglass.routine;

import com.hourglass.exception.ResourceNotFoundException;
import com.hourglass.user.User;
import com.hourglass.user.UserService;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoutineService {

    private final RoutineDao routineDao;
    private final UserService userService;
    private final RoutineRepository routineRepository;

    public RoutineService(RoutineDao routineDao, UserService userService, RoutineRepository routineRepository) {
        this.routineDao = routineDao;
        this.userService = userService;
        this.routineRepository = routineRepository;
    }

    public List<Routine> getRoutinesByUser() {
        User currentUser = userService.getCurrentUser();
        return routineDao.selectRoutinesByUser(currentUser);
    }

    public Routine createRoutine(RoutineCreationRequest routineCreationRequest) {
        User currentUser = userService.getCurrentUser();
        Routine routine = new Routine(routineCreationRequest.name(), routineCreationRequest.startDateTime(),
                routineCreationRequest.renewalInterval(), currentUser);
        return routineDao.insertRoutine(routine);
    }

    public void deleteRoutine(Long routineId) {
        Routine routine = routineRepository.findById(routineId)
                .orElseThrow(() -> new ResourceNotFoundException("Routine with id [%s] not found".formatted(routineId)));
        ensureUserOwnsRoutine(routine);
        routineDao.deleteRoutineById(routineId);
    }

    private void ensureUserOwnsRoutine(Routine routine) {
        User currentUser = userService.getCurrentUser();
        if (!routine.getUser().equals(currentUser)) {
            throw new AccessDeniedException("You are not authorized to delete this routine");
        }
    }
}
