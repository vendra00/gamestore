package com.gamestore.demo.repository.game;

import com.gamestore.demo.model.Game;
import com.gamestore.demo.model.dto.GameDto;

import java.util.List;

public interface GameService {
    List<GameDto> getAllGames();
    List<GameDto> getGamesByPlatformName(String platformName);
    GameDto getGameById(Long id);
    Game saveGame(Game gameDto);
    Game updateGame(Long id, Game gameDto);
    void deleteGameById(Long id);
}
