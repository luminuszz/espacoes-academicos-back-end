package com.ea.backend.domain.reservation.application.repository;

import com.ea.backend.domain.reservation.enterprise.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ReservationRepository extends JpaRepository<Reservation, UUID> {

    @Query(
            "SELECT r FROM reservations r WHERE r.academicSpace.id = :academicSpaceId AND " +
            "(r.startDateTime < :endDateTime AND r.endDateTime > :startDateTime)" + " AND r.status = 'CONFIRMED'"
    )
    List<Reservation> findOverlappingReservations(
            @Param("academicSpaceId") UUID academicSpaceId,
            @Param("startDateTime") LocalDateTime startDateTime,
            @Param("endDateTime") LocalDateTime endDateTime
    );

    Optional<Reservation> findReservationById(String id);

    @Query("SELECT r FROM reservations r WHERE r.endDateTime < :now AND r.status = 'PENDING'")
    List<Reservation> findExpiredReservations(@Param("now") LocalDateTime now);
}
