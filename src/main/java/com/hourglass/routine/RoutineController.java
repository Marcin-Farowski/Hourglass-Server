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

    @PutMapping("/{routineId}")
    public ResponseEntity<Routine> updateRoutine(@PathVariable Long routineId, @RequestBody RoutineUpdateRequest routineUpdateRequest) {
        Routine updatedRoutine = routineService.updateRoutine(routineId, routineUpdateRequest);
        return ResponseEntity.ok(updatedRoutine);
    }

    @PostMapping("/{routineId}/execute")
    public Routine executeRoutine(@PathVariable Long routineId) {
        return routineService.executeRoutine(routineId);
    }
}
