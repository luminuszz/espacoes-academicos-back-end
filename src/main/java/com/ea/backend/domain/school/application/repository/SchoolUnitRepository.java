package com.ea.backend.domain.school.application.repository;

import com.ea.backend.domain.school.enterprise.entity.SchoolUnit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SchoolUnitRepository extends JpaRepository<SchoolUnit, Long> {
}
