package com.gamestore.demo.controller.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Set;

public record GameDto(@NotBlank String title, @NotBlank String description, @NotNull @DecimalMin("0.01") Double price, Set<PlatformDto> platforms) {
}
