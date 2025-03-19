package com.ea.backend.domain.reservation.enterprise.entity;

public enum ReservationStatus {

    CONFIRMED("CONFIRMED"),
    CANCELED("CANCELED"),
    FINISHED("FINISHED"),
    PENDING("PENDING");

    private final String role;

    ReservationStatus(String status) {
        this.role = status;
    }

}
