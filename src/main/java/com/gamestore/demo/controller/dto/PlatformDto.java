package com.gamestore.demo.controller.dto;

import jakarta.validation.constraints.NotBlank;

public record PlatformDto(
        @NotBlank String name,
        @NotBlank String description)
{ }
