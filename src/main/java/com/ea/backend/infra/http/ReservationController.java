package com.ea.backend.infra.http;


import com.ea.backend.domain.reservation.application.dto.CreateReservationDto;
import com.ea.backend.domain.reservation.application.services.ReservationService;
import com.ea.backend.infra.security.UserAuthenticated;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    @Autowired
    private ReservationService reservationService;


    @PostMapping
    public ResponseEntity createReservation(@RequestBody @Valid CreateReservationDto dto, HttpServletRequest request) {

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
