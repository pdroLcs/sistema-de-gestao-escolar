package dev.pdrolcs.sistemagestaoescolar.auth;

import dev.pdrolcs.sistemagestaoescolar.auth.dto.LoginRequest;
import dev.pdrolcs.sistemagestaoescolar.config.TokenConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private TokenConfig tokenConfig;

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private AuthService authService;

    private LoginRequest loginRequest;
    private User user;
    private Authentication authentication;

    @BeforeEach
    void setup() {
        loginRequest = new LoginRequest("admin@email.com", "123456");
        user = User.builder()
                .email("admin@email.com")
                .password("encoded")
                .build();
        authentication = mock(Authentication.class);
    }

    @Test
    @DisplayName("Should login successfully and create jwt-token")
    void shouldLoginSuccessfully() {
        when(authenticationManager.authenticate(any())).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(user);
        when(tokenConfig.generateToken(user)).thenReturn("jwt-token");

        var response = authService.login(loginRequest);
        assertEquals("jwt-token", response.token());
    }

    @Test
    @DisplayName("Should throw exception when email not found")
    void shouldThrowExceptionWhenEmailNotFound() {
        when(authenticationManager.authenticate(any())).thenThrow(new UsernameNotFoundException("Usuário não encontrado"));

        assertThrows(UsernameNotFoundException.class, () -> authService.login(loginRequest));

        verify(tokenConfig, never()).generateToken(any());
    }

    @Test
    @DisplayName("Should throw exception when password is invalid")
    void shouldThrowExceptionWhenPasswordIsInvalid() {
        when(authenticationManager.authenticate(any())).thenThrow(new BadCredentialsException("Credenciais inválidas"));

        assertThrows(BadCredentialsException.class, () -> authService.login(loginRequest));

        verify(tokenConfig, never()).generateToken(any());
    }
}