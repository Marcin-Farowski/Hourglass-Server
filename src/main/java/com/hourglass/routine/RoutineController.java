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
    public ResponseEntity<Routine> createRoutine(@RequestBody RoutineCreationRequest routineCreationRequest) {
        Routine createdRoutine = routineService.createRoutine(routineCreationRequest);
        return ResponseEntity.status(201).body(createdRoutine);
    }

    @DeleteMapping("/{routineId}")
    public ResponseEntity<Void> deleteRoutine(@PathVariable Long routineId) {
        routineService.deleteRoutine(routineId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/complete")
    public Routine completeRoutine(@PathVariable Long id) {
        return routineService.completeRoutine(id);
    }

//    @PostMapping("/{id}/resetCurrentCompletion")
//    public Routine resetRoutine(@PathVariable Long id) {
//        return routineService.resetRoutine(id);
//    }

}
