package com.gamestore.demo.repository;

import com.gamestore.demo.model.Game;

import java.util.List;

public interface GameService {
    List<Game> getAllGames();
    Game getGameById(Long id);
    Game saveGame(Game game);
    Game updateGame(Long id, Game game);
    void deleteGameById(Long id);
}
