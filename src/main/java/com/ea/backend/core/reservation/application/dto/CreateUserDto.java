package com.ea.backend.core.reservation.application.dto;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class CreateUserDto {
    @NotNull
    private String name;

    @NotNull
    private String password;

    @NotNull
    @Email
    private String email;

    @NotNull
    private String role;

}
