package recruitment.security.dto;

import lombok.Data;

@Data
public class RegistrationResponse {
    private String message;
    private Long userId;
    private String username;
    private String role;

    public RegistrationResponse(String message, Long userId, String username, String role) {
        this.message = message;
        this.userId = userId;
        this.username = username;
        this.role = role;
    }
}
