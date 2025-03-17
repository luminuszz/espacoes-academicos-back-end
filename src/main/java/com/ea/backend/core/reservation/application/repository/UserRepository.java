package com.ea.backend.core.reservation.application.repository;

import com.ea.backend.core.reservation.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    Object findUserByEmail(String email);
}
