package com.hourglass.routine;

import com.hourglass.user.User;
import com.hourglass.user.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoutineService {

    private final RoutineDao routineDao;
    private final UserService userService;

    public RoutineService(RoutineDao routineDao, UserService userService) {
        this.routineDao = routineDao;
        this.userService = userService;
    }

    public List<Routine> getRoutinesByUser() {
        User user = userService.getCurrentUser();
        return routineDao.selectRoutinesByUser(user);
    }

    public Routine createRoutine(RoutineCreationRequest routineCreationRequest) {
        User user = userService.getCurrentUser();
        Routine routine = new Routine(routineCreationRequest.name(), routineCreationRequest.startDateTime(),
                routineCreationRequest.renewalInterval(), user);
        return routineDao.insertRoutine(routine);
    }
}
