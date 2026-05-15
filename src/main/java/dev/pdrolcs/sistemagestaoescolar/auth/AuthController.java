package dev.pdrolcs.sistemagestaoescolar.auth;

import dev.pdrolcs.sistemagestaoescolar.auth.dto.LoginRequest;
import dev.pdrolcs.sistemagestaoescolar.auth.dto.LoginResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }
}
