package com.ea.backend.infra.http;


import com.ea.backend.domain.reservation.application.dto.CreateReservationDto;
import com.ea.backend.domain.reservation.application.services.ReservationService;
import com.ea.backend.infra.security.UserAuthenticated;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    @PatchMapping("/{reservationId}/checkout")
    public ResponseEntity<?> updateReservationStatus(@PathVariable String reservationId, HttpServletRequest request) {
        try {

            var user = (UserAuthenticated) request.getAttribute("user");

            this.reservationService.markReservationAsCheckedOut(UUID.fromString(reservationId), user.getUser().getId());

            return ResponseEntity.ok().body("Reservation updated successfully");

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> createReservation(@RequestBody @Valid CreateReservationDto dto, HttpServletRequest request) {

        try {
            var authenticatedUser = (UserAuthenticated) request.getAttribute("user");

            dto.setUserId(authenticatedUser.getUser().getId().toString());

            this.reservationService.createReservation(dto);

            return ResponseEntity.ok().body("Reservation created successfully");

        }catch (RuntimeException e) {

          return ResponseEntity.badRequest().body(e.getLocalizedMessage());
        }
    }


}
