package com.hourglass.routine;

import com.hourglass.user.User;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class RoutineJPADataAccessService implements RoutineDao {

    private final RoutineRepository routineRepository;

    public RoutineJPADataAccessService(RoutineRepository routineRepository) {
        this.routineRepository = routineRepository;
    }

    @Override
    public List<Routine> selectRoutinesByUser(User user) {
        return routineRepository.findByUser(user);
    }

    @Override
    public Routine insertRoutine(Routine routine) {
        return routineRepository.save(routine);
    }

    @Override
    public void deleteRoutineById(Long routineId) {
        routineRepository.deleteById(routineId);
    }
}
