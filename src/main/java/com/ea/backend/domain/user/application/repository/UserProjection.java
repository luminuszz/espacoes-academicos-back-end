package com.ea.backend.domain.user.application.repository;

import com.ea.backend.domain.school.enterprise.entity.SchoolUnit;
import com.ea.backend.domain.user.enterprise.entity.UserRole;

import java.util.UUID;

public interface UserProjection {
  UUID getId();

  String getName();

  String getEmail();

  UserRole getRole();

  String getContactNumber();

  String getCourse();

  SchoolUnit getSchoolUnit();
}
