package com.gamestore.demo.repository;

import com.gamestore.demo.exceptions.game.GameAlreadyExistsException;
import com.gamestore.demo.exceptions.game.GameListEmptyException;
import com.gamestore.demo.exceptions.game.GameNotFoundException;
import com.gamestore.demo.model.Game;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class GameServiceImpl implements GameService {

    @Autowired
    private GameRepository gameRepository;

    @Override
    public List<Game> getAllGames() {
        log.info("Retrieving all games from the database");
        List<Game> games = gameRepository.findAll();
        if (games.isEmpty()) {
            throw new GameListEmptyException();
        }
        log.info("Found {} games in the database", games.size());
        return games;
    }

    @Override
    public Game getGameById(Long id) {
        log.info("Retrieving game with ID {} from the database", id);
        return gameRepository.findById(id).orElseThrow(() -> new GameNotFoundException(id));
    }

    @Override
    public Game saveGame(Game game) {
        log.info("Saving new game with title {} to the database", game.getTitle());
        try {
            return gameRepository.save(game);
        } catch (DataIntegrityViolationException e) {
            log.warn("Could not save game with title {} to the database because it already exists", game.getTitle());
            throw new GameAlreadyExistsException(game.getTitle());
        }
    }


    @Override
    public Game updateGame(Long id, Game game) {
        log.info("Updating game with ID {} in the database", id);
        Optional<Game> optionalGame = gameRepository.findById(id);
        if (optionalGame.isPresent()) {
            Game existingGame = optionalGame.get();
            setEditedGameValues(game, existingGame);
            return gameRepository.save(existingGame);
        } else {
            throw new GameNotFoundException(id);
        }
    }

    private static void setEditedGameValues(Game game, Game existingGame) {
        existingGame.setTitle(game.getTitle());
        existingGame.setDescription(game.getDescription());
        existingGame.setPrice(game.getPrice());
    }

    @Override
    public void deleteGameById(Long id) {
        log.info("Deleting game with ID {} from the database", id);
        Optional<Game> optionalGame = gameRepository.findById(id);
        if (optionalGame.isPresent()) {
            gameRepository.deleteById(id);
        } else {
            throw new GameNotFoundException(id);
        }
    }
}

