package com.ea.backend.domain.reservation.application.dto;
import com.ea.backend.domain.reservation.enterprise.entity.UserRole;
import jakarta.validation.constraints.*;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateUserDto {
    @NotBlank
    private String name;

    @NotBlank
    private String password;

    @NotBlank
    @Email
    private String email;


    private UserRole role;

}
