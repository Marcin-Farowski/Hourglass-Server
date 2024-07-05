package com.hourglass.routine;

import java.time.LocalDateTime;

public record RoutineCreationRequest(
        String name, LocalDateTime startDateTime, TimeInterval renewalInterval) {

}
