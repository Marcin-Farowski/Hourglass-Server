package com.hourglass.routine;

import com.hourglass.user.User;

import java.util.List;
import java.util.Optional;

public interface RoutineDao {
    List<Routine> selectRoutinesByUser(User user);
    Routine insertRoutine(Routine routine);
    void deleteRoutineById(Long routineId);

}
