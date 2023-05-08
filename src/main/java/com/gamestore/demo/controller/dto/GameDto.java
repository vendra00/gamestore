package com.gamestore.demo.controller.dto;

import com.gamestore.demo.model.enums.Genre;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;

import java.util.Date;
import java.util.Set;

public record GameDto(
        @NotBlank String title,
        @NotBlank String description,
        @NotNull @DecimalMin("0.01") Double price,
        @NotNull Genre genre,
        @NotNull @PastOrPresent Date releaseDate,
        @NotNull Boolean singlePlayer,
        @NotNull Boolean multiPlayer,
        @NotNull @PastOrPresent Date lastUpdated,
        @NotNull Set<PlatformDto> platforms,
        @NotNull PublisherForGameDto publisherDto
) {
}
