package com.gamestore.demo.mapper;

import com.gamestore.demo.model.Game;
import com.gamestore.demo.model.Platform;
import com.gamestore.demo.controller.dto.GameDto;

import java.util.stream.Collectors;

public class GameMapper {
    public static GameDto toDto(Game game) {
        return new GameDto(
                game.getTitle(),
                game.getDescription(),
                game.getPrice(),
                game.getPlatforms().stream().map(Platform::toDto).collect(Collectors.toSet())
        );
    }
}
