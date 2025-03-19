package com.ea.backend.domain.space.application.repository;

import com.ea.backend.domain.space.enterprise.AcademicSpace;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface AcademicSpaceRepository  extends JpaRepository<AcademicSpace, UUID> {
    Optional<AcademicSpace> findAcademicSpaceByAcronym(String acronym);
    Optional<AcademicSpace> findAcademicSpaceById(UUID id);
}
