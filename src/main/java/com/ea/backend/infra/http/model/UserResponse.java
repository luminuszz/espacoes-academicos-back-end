package com.ea.backend.infra.http.model;

import com.ea.backend.domain.user.enterprise.entity.UserRole;

public record UserResponse(String id, String email, String name, UserRole role) {}
