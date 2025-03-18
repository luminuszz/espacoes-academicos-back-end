package com.ea.backend.domain.reservation.application.services;

import com.ea.backend.domain.reservation.application.dto.CreateReservationDto;
import com.ea.backend.domain.reservation.application.repository.ReservationRepository;
import com.ea.backend.domain.reservation.application.repository.UserRepository;
import com.ea.backend.domain.reservation.enterprise.entity.Reservation;
import com.ea.backend.domain.space.application.AcademicSpaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AcademicSpaceRepository academicSpaceRepository;

    public void createReservation(CreateReservationDto dto) {

        var user = this.userRepository.findUserById(UUID.fromString(dto.getUserId()))
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        var academicSpace = this.academicSpaceRepository.findAcademicSpaceById(UUID.fromString(dto.getAcademicSpaceId()))
                .orElseThrow(() -> new IllegalArgumentException("Academic Space not found"));

        var reservation = new Reservation();

        reservation.setUser(user);
        reservation.setAcademicSpace(academicSpace);
        reservation.setStartDateTime(dto.getStartDateTime());
        reservation.setEndDateTime(dto.getEndDateTime());

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

}
