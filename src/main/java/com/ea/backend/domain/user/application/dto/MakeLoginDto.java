package com.ea.backend.domain.user.application.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MakeLoginDto {

    @NotBlank
    @Email
    private String email;

    @NotNull
    @NotBlank
    private String password;

}
