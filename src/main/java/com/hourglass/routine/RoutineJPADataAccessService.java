package com.hourglass.routine;

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
    public List<Routine> selectAllRoutines() {
        return routineRepository.findAll();
    }

    @Override
    public Optional<Routine> selectRoutineById(int id) {
        return Optional.empty();
    }

}
