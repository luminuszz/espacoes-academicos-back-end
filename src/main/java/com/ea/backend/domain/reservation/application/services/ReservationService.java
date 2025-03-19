package com.ea.backend.domain.reservation.application.services;

import com.ea.backend.domain.reservation.application.dto.CreateReservationDto;
import com.ea.backend.domain.reservation.application.repository.ReservationRepository;
import com.ea.backend.domain.reservation.enterprise.entity.Reservation;
import com.ea.backend.domain.reservation.enterprise.entity.ReservationStatus;
import com.ea.backend.domain.space.application.repository.AcademicSpaceRepository;
import com.ea.backend.domain.user.application.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AcademicSpaceRepository academicSpaceRepository;


    @Transactional
    public void createReservation(CreateReservationDto dto) {

        var user = this.userRepository.findUserById(UUID.fromString(dto.getUserId()))
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        var academicSpace = this.academicSpaceRepository.findAcademicSpaceById(UUID.fromString(dto.getAcademicSpaceId()))
                .orElseThrow(() -> new IllegalArgumentException("Academic Space not found"));

        var reservation = new Reservation();

        if(!academicSpace.isAvailable()) {
            throw new IllegalArgumentException("Space is not available");
        }


        reservation.setUser(user);
        reservation.setAcademicSpace(academicSpace);
        reservation.setStartDateTime(dto.getStartDateTime());
        reservation.setEndDateTime(dto.getEndDateTime());
        reservation.setStatus(ReservationStatus.CONFIRMED);

        var overlappingReservations = this.reservationRepository.findOverlappingReservations(
                academicSpace.getId(),
                reservation.getStartDateTime(),
                reservation.getEndDateTime()
        );

        if(!overlappingReservations.isEmpty()) {
            throw new IllegalArgumentException("There is already a reservation for this space in this period");
        }

        this.reservationRepository.save(reservation);

    }

    @Transactional
    public void cancelReservation(UUID reservationId) {
        var reservation = this.reservationRepository.findReservationById(reservationId.toString())
                        .orElseThrow(() -> new IllegalArgumentException("Reservation not found"));

        if(reservation.getStatus() == ReservationStatus.FINISHED) {
            throw new IllegalArgumentException("Not possible to cancel a finished reservation");
        }

        reservation.setStatus(ReservationStatus.CANCELED);

        this.reservationRepository.save(reservation);
    }

    public void markReservationAsCheckedOut(UUID reservationId) {
        var reservation = this.reservationRepository.findReservationById(reservationId.toString())
                .orElseThrow(() -> new IllegalArgumentException("Reservation not found"));

        if (reservation.getStatus() == ReservationStatus.FINISHED) {
            throw new IllegalArgumentException("Not possible to checkout a finished reservation");
        }

        reservation.setStatus(ReservationStatus.FINISHED);

        this.reservationRepository.save(reservation);
    }

    @Transactional
    public void updateExpiredReservations() {
        List<Reservation> expiredReservations = reservationRepository.findExpiredReservations(LocalDateTime.now());

        if (!expiredReservations.isEmpty()) {
            expiredReservations.forEach(reservation -> reservation.setStatus(ReservationStatus.FINISHED));
            reservationRepository.saveAll(expiredReservations);
        }
    }

}
