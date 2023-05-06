package com.gamestore.demo.service.game;

import com.gamestore.demo.exceptions.GameStoreException;
import com.gamestore.demo.model.Game;
import com.gamestore.demo.controller.dto.GameDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface GameService {
    Page<GameDto> getAllGames(Pageable pageable);
    List<GameDto> getGamesByPlatformName(String platformName);
    GameDto getGameById(Long id);
    List<Game> saveGame(List<Game> gameDto) throws GameStoreException;
    Game updateGame(Long id, Game gameDto);
    void deleteGameById(Long id);
}