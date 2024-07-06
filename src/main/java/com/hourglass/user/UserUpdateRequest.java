package com.hourglass.user;

import java.util.Date;

public record UserUpdateRequest(
        String firstName,
        String lastName,
        Date dateOfBirth,
        Gender gender,
        String email
) {
}
