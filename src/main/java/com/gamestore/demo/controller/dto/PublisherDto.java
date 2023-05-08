package com.gamestore.demo.controller.dto;

import com.gamestore.demo.model.enums.Country;
import jakarta.validation.constraints.NotBlank;

import java.util.Set;

public record PublisherDto(@NotBlank String name,
                           @NotBlank Country country,
                           @NotBlank Set<GameDto> games)
{ }
