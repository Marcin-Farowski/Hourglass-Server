package com.hourglass.user;

import com.hourglass.exception.DuplicateResourceException;
import com.hourglass.exception.RequestValidationException;
import com.hourglass.exception.UserNotFoundException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getCurrentUser() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email = userDetails.getUsername();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
    }

    public void updateUser(UserUpdateRequest userUpdateRequest) {
        User currentUser = getCurrentUser();

        boolean changes = false;

        if (userUpdateRequest.firstName() != null &&
                !userUpdateRequest.firstName().equals(currentUser.getFirstName())) {
            currentUser.setFirstName(userUpdateRequest.firstName());
            changes = true;
        }

        if (userUpdateRequest.lastName() != null &&
                !userUpdateRequest.lastName().equals(currentUser.getLastName())) {
            currentUser.setLastName(userUpdateRequest.lastName());
            changes = true;
        }

        if (userUpdateRequest.email() != null &&
                !userUpdateRequest.email().equals(currentUser.getEmail())) {
            if (userRepository.existsUserByEmail(userUpdateRequest.email())) {
                throw new DuplicateResourceException("E-mail already taken");
            }
            currentUser.setEmail(userUpdateRequest.email());
            changes = true;
        }

        if (!changes) {
            throw new RequestValidationException("No data changes found");
        }

        userRepository.save(currentUser);
    }
}
