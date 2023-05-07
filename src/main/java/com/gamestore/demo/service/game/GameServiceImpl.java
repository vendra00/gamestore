package com.gamestore.demo.service.game;

import com.gamestore.demo.controller.dto.GameDto;
import com.gamestore.demo.exceptions.game.GameAlreadyExistsException;
import com.gamestore.demo.exceptions.game.GameListEmptyException;
import com.gamestore.demo.exceptions.game.GameNotFoundException;
import com.gamestore.demo.mapper.GameMapper;
import com.gamestore.demo.model.Game;
import com.gamestore.demo.model.Platform;
import com.gamestore.demo.model.enums.Genre;
import com.gamestore.demo.repository.GameRepository;
import com.gamestore.demo.repository.PlatformRepository;
import com.gamestore.demo.service.game.utils.GameUtils;
import com.gamestore.demo.service.validation.GameValidator;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Transactional
@Service
public class GameServiceImpl implements GameService {

    private final GameRepository gameRepository;
    private final PlatformRepository platformRepository;

    @Autowired
    public GameServiceImpl(GameRepository gameRepository, PlatformRepository platformRepository) {
        this.gameRepository = gameRepository;
        this.platformRepository = platformRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<GameDto> getAllGames(Pageable pageable) {
        log.info("Retrieving all games from the database. Page number: {}, page size: {}.", pageable.getPageNumber(), pageable.getPageSize());
        Page<Game> gamePage = gameRepository.findAll(pageable);
        if (gamePage.isEmpty()) {
            log.warn("No games found in the database.");
            throw new GameListEmptyException("No games found in the database.");
        }
        Page<GameDto> gameDtoPage = gamePage.map(GameMapper::toGameDto);
        log.info("Found {} games in the database.", gameDtoPage.getTotalElements());
        return gameDtoPage;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<GameDto> getGamesByPlatformName(String platformName, Pageable pageable) {
        log.info("Retrieving all games with platform name {} from the database. Page number: {}, page size: {}.", platformName, pageable.getPageNumber(), pageable.getPageSize());
        Page<Game> gamePage = gameRepository.findByPlatformName(platformName, pageable);
        if (gamePage.isEmpty()) {
            log.warn("No games found in the database with platform name {}.", platformName);
            throw new GameListEmptyException("No games found in the database with platform name " + platformName + ".");
        }
        Page<GameDto> gameDtoPage = gamePage.map(GameMapper::toGameDto);
        log.info("Found {} games in the database with platform name {}.", gameDtoPage.getTotalElements(), platformName);
        return gameDtoPage;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<GameDto> getGamesByGenre(Genre genre, Pageable pageable) {
        Page<Game> games = gameRepository.findByGenre(genre, pageable);
        if (games.isEmpty()) {
            log.warn("No games found in the database with genre name {}.", genre);
            throw new GameListEmptyException("No games found in the database with genre name " + genre + ".");
        }
        Page<GameDto> gameDtoPage = games.map(GameMapper::toGameDto);
        log.info("Found {} games in the database with platform name {}.", gameDtoPage.getTotalElements(), genre);
        return gameDtoPage;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<GameDto> getGamesByReleaseYear(int year, Pageable pageable) {
        Page<Game> games = gameRepository.findByReleaseYear(year, pageable);
        if (games.isEmpty()) {
            log.warn("No games found in the database released in the year {}.", year);
            throw new GameListEmptyException("No games found in the database released in the year " + year + ".");
        }
        Page<GameDto> gameDtoPage = games.map(GameMapper::toGameDto);
        log.info("Found {} games in the database released in the year {}.", gameDtoPage.getTotalElements(), year);
        return gameDtoPage;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<GameDto> getGamesByTitle(String title, Pageable pageable) {
        Page<Game> games = gameRepository.findByTitleContainingIgnoreCase(title, pageable);
        if (games.isEmpty()) {
            log.warn("No games found in the database with title containing {}.", title);
            throw new GameListEmptyException("No games found in the database with title containing " + title + ".");
        }
        Page<GameDto> gameDtoPage = games.map(GameMapper::toGameDto);
        log.info("Found {} games in the database with title containing {}.", gameDtoPage.getTotalElements(), title);
        return gameDtoPage;
    }


    @Override
    @Transactional(readOnly = true)
    public GameDto getGameById(Long id) {
        log.info("Retrieving game with ID {} from the database", id);
        Game game = gameRepository.findById(id).orElseThrow(() -> new GameNotFoundException(id));

        // Eagerly fetch platforms collection
        Hibernate.initialize(game.getPlatforms());

        return GameMapper.toGameDto(game);
    }

    @Override
    @Transactional
    public List<Game> saveGame(List<Game> games) {
        List<Game> savedGames = games.stream()
                .filter(GameValidator::isValidGame)
                .peek(game -> log.info("Saving new game with title {} to the database", game.getTitle()))
                .map(game -> {
                    Set<Platform> selectedPlatforms = GameValidator.getValidPlatforms(game.getPlatforms(), platformRepository);
                    game.setPlatforms(selectedPlatforms);
                    game.setLastUpdated(new Date());
                    try {
                        return gameRepository.save(game);
                    } catch (DataIntegrityViolationException e) {
                        log.warn("Could not save game with title {} to the database because it already exists", game.getTitle());
                        throw new GameAlreadyExistsException(game.getTitle());
                    }
                })
                .collect(Collectors.toList());


        List<Game> result = gameRepository.saveAll(savedGames);
        log.info("Saved {} new games to the database", result.size());
        return result;
    }




    @Override
    @Transactional
    public Game updateGame(Long id, Game game) {
        log.info("Updating game with ID {} in the database", id);
        Game existingGame = gameRepository.findById(id).orElseThrow(() -> new GameNotFoundException(id));
        GameUtils.setEditedGameValues(game, existingGame);
        return gameRepository.save(existingGame);
    }

    @Override
    @Transactional
    public void deleteGameById(Long id) {
        log.info("Attempting to delete game with ID {} from the database", id);
        Optional<Game> optionalGame = gameRepository.findById(id);
        if (optionalGame.isPresent()) {
            Game gameToDelete = optionalGame.get();
            log.info("Deleting game with ID {} from the database. Title: {}", id, gameToDelete.getTitle());
            gameRepository.deleteById(id);
            log.info("Game with ID {} deleted successfully from the database.", id);
        } else {
            log.warn("Could not find game with ID {} in the database for deletion.", id);
            throw new GameNotFoundException(id);
        }
    }
}

