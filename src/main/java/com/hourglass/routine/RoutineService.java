package com.hourglass.routine;

import com.hourglass.exception.ResourceNotFound;
import com.hourglass.user.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoutineService {

    private final RoutineDao routineDao;

    public RoutineService(RoutineDao routineDao) {
        this.routineDao = routineDao;
    }

    public List<Routine> getRoutinesByUser(User user) {
        return routineDao.selectRoutinesByUser(user);
    }
}
