package com.gamestore.demo.mapper;

import com.gamestore.demo.controller.dto.GameDto;
import com.gamestore.demo.controller.dto.PlatformDto;
import com.gamestore.demo.controller.dto.PublisherForGameDto;
import com.gamestore.demo.model.Game;

import java.util.stream.Collectors;

public class GameMapper {
    public static GameDto toGameDto(Game game) {
        return new GameDto(
                game.getTitle(),
                game.getDescription(),
                game.getPrice(),
                game.getGenre(),
                game.getReleaseDate(),
                game.getSinglePlayer(),
                game.getMultiPlayer(),
                game.getLastUpdated(),
                game.getPlatforms().stream()
                        .map(platform -> new PlatformDto(
                                platform.getName(),
                                platform.getDescription()
                        ))
                        .collect(Collectors.toSet()),
                new PublisherForGameDto(
                        game.getPublisher().getName(),
                        game.getPublisher().getCountry()
                )
        );
    }
}

