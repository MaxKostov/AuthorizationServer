package recruitment.security.controllers;

import recruitment.security.dto.RegistrationRequest;
import recruitment.security.dto.RegistrationResponse;
import recruitment.security.entities.User;
import recruitment.security.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class RegistrationController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegistrationRequest request) {
        try {
            User user = userService.registerUser(request);

            RegistrationResponse response = new RegistrationResponse(
                    "User registered successfully",
                    user.getId(),
                    user.getUsername(),
                    user.getRole()
            );

            return ResponseEntity.status(HttpStatus.CREATED).body(response);

        } catch (RuntimeException e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }

    // Optional: Check if username is available
    @GetMapping("/check-username")
    public ResponseEntity<?> checkUsernameAvailability(@RequestParam String username) {
        boolean exists = userService.existsByUsername(username);
        Map<String, Object> response = new HashMap<>();
        response.put("username", username);
        response.put("available", !exists);
        response.put("message", exists ? "Username already taken" : "Username available");
        return ResponseEntity.ok(response);
    }
}