package com.hourglass.routine;

import java.util.List;
import java.util.Optional;

public interface RoutineDao {
    List<Routine> selectAllRoutines();
    Optional<Routine> selectRoutineById(int id);
}
