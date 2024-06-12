package com.hourglass.routine;

import com.hourglass.exception.ResourceNotFound;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoutineService {

    private final RoutineDao routineDao;

    public RoutineService(RoutineDao routineDao) {
        this.routineDao = routineDao;
    }

    public List<Routine> getAllRoutines() {
        return routineDao.selectAllRoutines();
    }

    public Routine getRoutine(Integer id) {
        return routineDao.selectRoutineById(id)
                .orElseThrow(() -> new ResourceNotFound("Routine with id " + id + " not found"));
    }
}
