package com.ea.backend.domain.reservation.application.services;

import com.ea.backend.domain.reservation.application.dto.CreateReservationDto;
import com.ea.backend.domain.reservation.application.repository.ReservationRepository;
import com.ea.backend.domain.reservation.enterprise.entity.Reservation;
import com.ea.backend.domain.reservation.enterprise.entity.ReservationStatus;
import com.ea.backend.domain.reservation.enterprise.events.ReservationCanceledEvent;
import com.ea.backend.domain.space.application.repository.AcademicSpaceRepository;
import com.ea.backend.domain.user.application.repository.UserRepository;
import com.ea.backend.shared.DomainException;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
public class ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AcademicSpaceRepository academicSpaceRepository;


    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;


    @Transactional
    public void createReservation(CreateReservationDto dto) {

        var user = this.userRepository.findUserById(UUID.fromString(dto.getUserId()))
                .orElseThrow(() -> new DomainException("User not found"));

        var academicSpace = this.academicSpaceRepository.findAcademicSpaceById(UUID.fromString(dto.getAcademicSpaceId()))
                .orElseThrow(() -> new DomainException("Academic Space not found"));

        var reservation = new Reservation();

        if(!academicSpace.isAvailable()) {
            throw new DomainException("Space is not available");
        }

        var userHasPendingReservation = this.reservationRepository.findReservationsByUserIdAndStatus(user.getId(), ReservationStatus.PENDING);

        if (!userHasPendingReservation.isEmpty()) {
            throw new DomainException("User already has a pending reservation");
        }


        reservation.setUser(user);
        reservation.setAcademicSpace(academicSpace);
        reservation.setStartDateTime(dto.getStartDateTime());
        reservation.setEndDateTime(dto.getEndDateTime());
        reservation.setStatus(ReservationStatus.PENDING);

        var reservationIntervalIsValid = reservation.getStartDateTime().isAfter(reservation.getEndDateTime());
        var reservationIntervalIsLessThanMinimum = reservation.getStartDateTime().isBefore(LocalDateTime.now().plusMinutes(30));

        var isValidReservation = !reservationIntervalIsValid && !reservationIntervalIsLessThanMinimum;


        if (!isValidReservation) {
            throw new DomainException("Invalid reservation interval");
        }


        var overlappingReservations = this.reservationRepository.findOverlappingReservations(
                academicSpace.getId(),
                reservation.getStartDateTime(),
                reservation.getEndDateTime()
        );

        if(!overlappingReservations.isEmpty()) {
            throw new DomainException("There is already a reservation for this space in this period");
        }

        this.reservationRepository.save(reservation);

    }

    @Transactional
    public void cancelReservation(UUID reservationId) {
        var reservation = this.reservationRepository.findReservationById(reservationId.toString())
                .orElseThrow(() -> new DomainException("Reservation not found"));

        if (List.of(ReservationStatus.CONFIRMED_BY_THE_ENTERPRISE, ReservationStatus.CONFIRMED_BY_THE_ENTERPRISE).contains(reservation.getStatus())) {
            throw new DomainException("Not possible to cancel a finished reservation");
        }

        reservation.setStatus(ReservationStatus.CANCELED);

        this.reservationRepository.save(reservation);

        this.applicationEventPublisher.publishEvent(new ReservationCanceledEvent(reservation));
    }

    public void markReservationAsCheckedOut(UUID reservationId, UUID userId) {
        var reservation = this.reservationRepository.findReservationByIdAndUser_Id(reservationId.toString(), userId)
                .orElseThrow(() -> new DomainException("Reservation not found"));


        if (reservation.isConfirmed()) {
            throw new DomainException("Not possible to checkout a finished reservation");
        }

        reservation.setStatus(ReservationStatus.CONFIRMED_BY_THE_USER);

        this.reservationRepository.save(reservation);
    }

    @Transactional
    public void updateExpiredReservations() {
        List<Reservation> expiredReservations = reservationRepository.findExpiredReservations(LocalDateTime.now());

        if (!expiredReservations.isEmpty()) {
            expiredReservations.forEach(reservation -> reservation.setStatus(ReservationStatus.CONFIRMED_BY_THE_ENTERPRISE));
            reservationRepository.saveAll(expiredReservations);
        }
    }

}
