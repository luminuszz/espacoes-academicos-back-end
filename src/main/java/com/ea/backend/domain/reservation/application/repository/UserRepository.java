package com.ea.backend.domain.reservation.application.repository;

import com.ea.backend.domain.reservation.enterprise.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findUserByEmail(String email);

}
