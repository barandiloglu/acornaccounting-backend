package backend.acornaccounting.backend.dto;

import lombok.Data;

@Data
public class UserResponse {
    private String message;
    private String token; // Optional: Add token for authentication
    private String firstName;
    private String lastName;
    private String role; // Useful for displaying the user's role
}

