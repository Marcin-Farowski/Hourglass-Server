package com.hourglass.routine;

import com.hourglass.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoutineRepository extends JpaRepository<Routine, Long> {
    List<Routine> findRoutinesByUser(User user);
}
