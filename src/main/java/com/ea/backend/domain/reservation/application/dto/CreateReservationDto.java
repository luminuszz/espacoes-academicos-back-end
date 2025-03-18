package com.ea.backend.domain.reservation.application.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.UUID;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Setter
@Getter
public class CreateReservationDto {
    @UUID
    @NotBlank
    @NotNull
    private String academicSpaceId;


    @DateTimeFormat
    @NotNull
    private LocalDateTime startDateTime;

    @DateTimeFormat
    @NotNull
    private LocalDateTime endDateTime;


    private String userId;

}
