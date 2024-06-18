package com.hourglass.routine;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/routines")
public class RoutineController {

    private final RoutineService routineService;


    public RoutineController(RoutineService routineService) {
        this.routineService = routineService;
    }

    @GetMapping
    public List<Routine> getRoutinesByUser() {
        return routineService.getRoutinesByUser();
    }

    @PostMapping
    public ResponseEntity<Routine> createRoutine(@RequestBody RoutineCreationRequest routine) {
        Routine createdRoutine = routineService.createRoutine(routine);
        return ResponseEntity.status(201).body(createdRoutine);
    }

//    @PostMapping("/{id}/complete")
//    public Routine completeRoutine(@PathVariable Long id) {
//        return routineService.completeRoutine(id);
//    }
//
//    @PostMapping("/{id}/reset")
//    public Routine resetRoutine(@PathVariable Long id) {
//        return routineService.resetRoutine(id);
//    }

}
