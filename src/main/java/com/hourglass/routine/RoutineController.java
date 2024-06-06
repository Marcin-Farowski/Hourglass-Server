package com.hourglass.routine;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RoutineController {

    @GetMapping
    public List<Routine> getRoutines() {
        // TODO return routines
    }

}
