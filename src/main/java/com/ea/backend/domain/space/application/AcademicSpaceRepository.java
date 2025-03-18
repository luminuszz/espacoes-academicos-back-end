package com.ea.backend.domain.space.application;

import com.ea.backend.domain.space.enterprise.AcademicSpace;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface AcademicSpaceRepository  extends JpaRepository<AcademicSpace, UUID> {
    Optional<AcademicSpace> findByRoomName(String name);
    Optional<AcademicSpace> findAcademicSpaceById(UUID id);
}
