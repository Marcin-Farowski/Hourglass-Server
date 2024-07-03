package com.hourglass.routineExecution;

import com.hourglass.routine.RoutineExecutionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/routineExecutions")
public class RoutineExecutionController {

    private final RoutineExecutionService routineExecutionService;


    public RoutineController(RoutineExecutionService routineExecutionService) {
        this.routineExecutionService = routineExecutionService;
    }

    @DeleteMapping("/{routineExecutionId}")
    public ResponseEntity<Void> deleteRoutineExecution(@PathVariable Long routineExecutionId) {
        routineExecutionService.deleteRoutineExecution(routineExecutionId);
        return ResponseEntity.noContent().build();
    }
}
