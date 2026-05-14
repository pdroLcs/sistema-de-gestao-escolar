package dev.pdrolcs.sistemagestaoescolar.auth;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<UserDetails> findUserByEmail(String username);

    boolean existsByRolesContaining(Role role);

}
