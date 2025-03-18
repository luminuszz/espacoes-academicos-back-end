package com.ea.backend.domain.reservation.enterprise.entity;

import lombok.Getter;

@Getter
public enum UserRole {
    ADMIN("admin"),
    TEACHER("teacher");

    private final String role;

    UserRole(String role) {
        this.role = role;
    }

}
