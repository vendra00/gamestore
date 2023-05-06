package com.gamestore.demo.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Value;

import java.util.Set;

@Value
public class GameDto {
    @JsonIgnore
    Long id;
    @NotBlank
    String title;
    @NotBlank
    String description;
    @NotNull
    @DecimalMin("0.01")
    Double price;
    Set<PlatformDto> platforms;
}
