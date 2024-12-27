package backend.acornaccounting.backend.controller;

import backend.acornaccounting.backend.dto.UserRequest;
import backend.acornaccounting.backend.dto.UserResponse;
import backend.acornaccounting.backend.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@RequestBody UserRequest request) {
        UserResponse response = authService.register(request);
        return ResponseEntity.ok(response);
    }


    @PostMapping("/login")
    public ResponseEntity<UserResponse> login(@RequestBody UserRequest request) {
        UserResponse response = authService.login(request);
        return ResponseEntity.ok(response);
    }

}
