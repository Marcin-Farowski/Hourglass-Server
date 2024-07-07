package com.hourglass.user;

import java.time.LocalDate;

public record UserUpdateRequest(
        String firstName,
        String lastName,
        LocalDate dateOfBirth,
        Gender gender,
        String email
) {
}
