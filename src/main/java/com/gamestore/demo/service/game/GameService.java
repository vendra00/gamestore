package com.gamestore.demo.service.game;

import com.gamestore.demo.controller.dto.GameDto;
import com.gamestore.demo.model.Game;
import com.gamestore.demo.model.enums.Genre;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface GameService {
    Page<GameDto> getAllGames(Pageable pageable);
    Page<GameDto> getGamesByPlatformName(String platformName, Pageable pageable);
    GameDto getGameById(Long id);
    List<Game> saveGame(List<Game> gameDto);
    Game updateGame(Long id, Game gameDto);
    void deleteGameById(Long id);
    Page<GameDto> getGamesByGenre(Genre genre, Pageable pageable);
    Page<GameDto> getGamesByReleaseYear(int year, Pageable pageable);
    Page<GameDto> getGamesByTitle(String title, Pageable pageable);
    Page<GameDto> getGamesBySinglePlayer(boolean singlePlayer, Pageable pageable);
    Page<GameDto> getGamesByMultiPlayer(boolean multiPlayer, Pageable pageable);
}