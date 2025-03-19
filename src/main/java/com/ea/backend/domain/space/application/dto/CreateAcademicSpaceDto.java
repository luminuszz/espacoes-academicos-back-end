package com.ea.backend.domain.space.application.dto;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateAcademicSpaceDto {

    @NotNull
    @NotBlank
    private String name;


    @NotNull
    @NotBlank
    private String description;


    @NotNull
    @Positive
    @Min(1)
    private Integer capacity;


    @NotNull
    @NotBlank
    private String acronym;
}
