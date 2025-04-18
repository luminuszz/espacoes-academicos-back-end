package com.ea.backend.domain.reservation.application.services;

import com.ea.backend.domain.reservation.application.dto.CreateReservationDto;
import com.ea.backend.domain.reservation.application.repository.ReservationRepository;
import com.ea.backend.domain.reservation.enterprise.entity.Reservation;
import com.ea.backend.domain.reservation.enterprise.entity.ReservationStatus;
import com.ea.backend.domain.reservation.enterprise.events.ReservationCanceledEvent;
import com.ea.backend.domain.space.application.repository.AcademicSpaceRepository;
import com.ea.backend.domain.user.application.repository.UserRepository;
import com.ea.backend.shared.DomainException;
import com.ea.backend.shared.DomainExceptionCode;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

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

    private static final Logger logger = LoggerFactory.getLogger(ReservationService.class);


    private static boolean isIsValidReservation(Reservation reservation) {
        var reservationIntervalIsValid =
                reservation.getEndDateTime().isAfter(reservation.getStartDateTime());

        int MINIMUM_RESERVATION_TIME = 10; // 10 minutes;

        var reservationIntervalIsMoreOrEqualToMinimum =
                reservation
                        .getEndDateTime()
                        .isAfter(reservation.getStartDateTime().plusMinutes(MINIMUM_RESERVATION_TIME));


        return reservationIntervalIsValid && reservationIntervalIsMoreOrEqualToMinimum;
    }


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

        var userHasPendingReservation = this.reservationRepository.findReservationsByUserIdAndStatus(user.getId(), ReservationStatus.SCHEDULED);

        if (!userHasPendingReservation.isEmpty()) {
            throw new DomainException("User already has a pending reservation", DomainExceptionCode.USER_HAS_PENDING_RESERVATIONS);
        }


        reservation.setUser(user);
        reservation.setAcademicSpace(academicSpace);
        reservation.setStartDateTime(dto.getStartDateTime());
        reservation.setEndDateTime(dto.getEndDateTime());
        reservation.setStatus(ReservationStatus.SCHEDULED);

        var isValidReservation = isIsValidReservation(reservation);

        if (!isValidReservation) {
            throw new DomainException("Invalid reservation interval");
        }


        var overlappingReservations = this.reservationRepository.findOverlappingReservations(
                academicSpace.getId(),
                reservation.getStartDateTime(),
                reservation.getEndDateTime()
        );

        if(!overlappingReservations.isEmpty()) {
            throw new DomainException("There is already a reservation for this space in this period", DomainExceptionCode.RESERVATION_INTERVAL_OVERLAP);
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

  public Page<Reservation> fetchReservationsPaginated(int page, int pageSize) {
    return this.reservationRepository.findAllBy(PageRequest.of(page, pageSize));
  }

  public Page<Reservation> fetchReservationByUserIdAndStatusPaged(
      UUID userId, Optional<String> status, int page, int pageSize) {


      logger.info(status.toString());

      if (status.isPresent()) {
      return this.reservationRepository.findAllByUserIdAndStatus(
          userId, ReservationStatus.valueOf(status.get()), PageRequest.of(page, pageSize));
    }

      return this.reservationRepository.findAllByUserId(userId, PageRequest.of(page, pageSize));
  }


    public Page<Reservation> fetchReservationWithFilterPaginated(
            int page,
            int pageSize,
            Optional<String> filterColumn,
            Optional<String> filterValue
    ) {

        var pageRequest = PageRequest.of(page, pageSize);

        if (filterColumn.isEmpty() || filterValue.isEmpty()) {
            return this.reservationRepository.findAll(pageRequest);
        }

        var column = filterColumn.get();
        var value = filterValue.get();

        switch (column) {
            case "status" -> {
                return this.reservationRepository.findAllByStatus(ReservationStatus.valueOf(value), pageRequest);
            }
            case "teacher" -> {
                return this.reservationRepository.findAllByUserName(value, pageRequest);
            }
            case "space" -> {
                return this.reservationRepository.findAllByAcademicSpaceRoomName(value, pageRequest);
            }
            default -> {
                throw new DomainException("Invalid filter column");
            }
        }

    }
}
