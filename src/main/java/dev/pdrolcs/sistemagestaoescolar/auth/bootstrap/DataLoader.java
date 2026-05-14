package dev.pdrolcs.sistemagestaoescolar.auth.bootstrap;

import dev.pdrolcs.sistemagestaoescolar.auth.Role;
import dev.pdrolcs.sistemagestaoescolar.auth.User;
import dev.pdrolcs.sistemagestaoescolar.auth.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${app.admin.email}")
    private String adminEmail;

    @Value("${app.admin.password}")
    private String adminPassword;

    public DataLoader(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        if (!userRepository.existsByRolesContaining(Role.DIRECTOR)) {
            var director = User.builder()
                    .name("Diretor")
                    .email(adminEmail)
                    .password(passwordEncoder.encode(adminPassword))
                    .build();
            director.addRole(Role.DIRECTOR);
            userRepository.save(director);
        }
    }
}
