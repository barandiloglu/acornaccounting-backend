package backend.acornaccounting.backend.service;

import backend.acornaccounting.backend.dto.UserRequest;
import backend.acornaccounting.backend.dto.UserResponse;
import backend.acornaccounting.backend.model.User;
import backend.acornaccounting.backend.repository.UserRepository;
import backend.acornaccounting.backend.util.JwtUtil;
import backend.acornaccounting.backend.util.PhoneFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    public UserResponse register(UserRequest request) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
            LocalDate dateOfBirth = LocalDate.parse(request.getDateOfBirth(), formatter);
            
            String formattedPhone = PhoneFormatter.formatPhoneNumber(request.getPhone());

            User user = new User();
            user.setFirstName(request.getFirstName());
            user.setMiddleName(request.getMiddleName());
            user.setLastName(request.getLastName());
            user.setDateOfBirth(dateOfBirth); // Use parsed date
            user.setEmail(request.getEmail());
            user.setPhone(formattedPhone); // Store formatted phone number
            user.setPassword(passwordEncoder.encode(request.getPassword()));
            user.setRole("user");
    
            userRepository.save(user);
    
            UserResponse response = new UserResponse();
            response.setMessage("Registration successful!");
            return response;
        } catch (DateTimeParseException e) {
            throw new RuntimeException("Invalid date format: " + request.getDateOfBirth());
        }
    }

    public UserResponse login(UserRequest request) {
        Optional<User> user = userRepository.findByEmail(request.getEmail());
        if (user.isPresent() && passwordEncoder.matches(request.getPassword(), user.get().getPassword())) {
            String token = jwtUtil.generateToken(user.get().getEmail(), user.get().getRole());
            UserResponse response = new UserResponse();
            response.setMessage("Login successful");
            response.setToken(token);
            response.setRole(user.get().getRole()); // Include role in the response
            return response;
        }
        throw new RuntimeException("Invalid credentials");
    }
    
}
