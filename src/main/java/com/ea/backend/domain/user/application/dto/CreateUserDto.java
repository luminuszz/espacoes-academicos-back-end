package com.ea.backend.domain.user.application.dto;

import com.ea.backend.domain.user.enterprise.entity.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
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

    @NotBlank
    private String role;


    private String contactNumber;

    private String course;

    private String schoolUnitId;


    public UserRole toDomainRole() {
        return UserRole.valueOf(role);
    }

}
