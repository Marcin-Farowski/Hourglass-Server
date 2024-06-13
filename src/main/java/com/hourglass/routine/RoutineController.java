package com.hourglass.routine;

import com.hourglass.user.User;
import com.hourglass.user.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/routines")
public class RoutineController {

    private final RoutineService routineService;
    private final UserRepository userRepository;

    public RoutineController(RoutineService routineService, UserRepository userRepository) {
        this.routineService = routineService;
        this.userRepository = userRepository;
    }

    @GetMapping
    public List<Routine> getRoutines() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email = userDetails.getUsername();
        User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
        return routineService.getRoutinesByUser(user);
    }

}
