package com.hourglass.user;

import jakarta.persistence.Column;

public record UserUpdateRequest(
        String firstName,
        String lastName,
        String email
) {
}
