package dev.pdrolcs.sistemagestaoescolar.auth;

import dev.pdrolcs.sistemagestaoescolar.auth.dto.LoginRequest;
import dev.pdrolcs.sistemagestaoescolar.auth.dto.LoginResponse;
import dev.pdrolcs.sistemagestaoescolar.config.TokenConfig;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final TokenConfig tokenConfig;
    private final AuthenticationManager authenticationManager;

    public AuthService(TokenConfig tokenConfig, AuthenticationManager authenticationManager) {
        this.tokenConfig = tokenConfig;
        this.authenticationManager = authenticationManager;
    }

    public LoginResponse login(LoginRequest request) {
        var userAndPassword = new UsernamePasswordAuthenticationToken(request.email(), request.password());
        var authentication = authenticationManager.authenticate(userAndPassword);
        var user = (User) authentication.getPrincipal();
        String token = tokenConfig.generateToken(user);
        return new LoginResponse(token);
    }
}
