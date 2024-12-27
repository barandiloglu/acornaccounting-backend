package backend.acornaccounting.backend.dto;

import lombok.Data;

@Data
public class UserRequest {
    private String email;
    private String password;
    private String firstName;
    private String middleName;
    private String lastName;
    private String dateOfBirth; // Consider validating the format
    private String phone;
}
