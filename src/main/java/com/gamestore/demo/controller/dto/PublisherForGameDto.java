package com.gamestore.demo.controller.dto;

import com.gamestore.demo.model.enums.Country;
import jakarta.validation.constraints.NotBlank;

public record PublisherForGameDto(
        @NotBlank String name,
        @NotBlank Country country)
{
}
