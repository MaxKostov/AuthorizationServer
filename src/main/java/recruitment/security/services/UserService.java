package recruitment.security.services;

import recruitment.security.entities.User;
import recruitment.security.dto.RegistrationRequest;
import recruitment.security.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User registerUser(RegistrationRequest request) {
        // Check if username already exists
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("Username already registered");
        }

        // Validate role
        String role = validateRole(request.getRole());

        // Create new user
        User user = new User(
                request.getUsername(),
                passwordEncoder.encode(request.getPassword()),
                role
        );

        return userRepository.save(user);
    }

    private String validateRole(String role) {
        if (role == null || role.trim().isEmpty()) {
            return "USER";
        }

        // Add role validation logic here if needed
        // For example, only allow specific roles
        String normalizedRole = role.toUpperCase().startsWith("ROLE_") ? role.toUpperCase() : "ROLE_" + role.toUpperCase();

        // Optional: Validate against allowed roles
        if (!isValidRole(normalizedRole)) {
            throw new RuntimeException("Invalid role specified");
        }

        return normalizedRole;
    }

    private boolean isValidRole(String role) {
        // Define allowed roles
        return role.equals("ROLE_USER") || role.equals("ROLE_ADMIN") || role.equals("ROLE_RECRUITER");
    }

    // Add this method to your UserService class
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }
}