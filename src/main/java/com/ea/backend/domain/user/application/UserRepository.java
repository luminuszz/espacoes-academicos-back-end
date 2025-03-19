package com.ea.backend.domain.user.application;

import com.ea.backend.domain.user.enterprise.entity.User;
import com.ea.backend.domain.user.enterprise.entity.UserRole;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findUserByEmail(String email);
    Optional<User> findUserById(UUID userId);

    Page<User> findAllByRole(UserRole role, Pageable pageable);
}
