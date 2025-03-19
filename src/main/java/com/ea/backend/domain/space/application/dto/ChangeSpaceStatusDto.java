package com.ea.backend.domain.space.application.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChangeSpaceStatusDto {

    @NotNull
    @NotBlank
    private String status;


    private String spaceId;

}
