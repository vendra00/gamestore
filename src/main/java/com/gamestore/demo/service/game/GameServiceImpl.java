package com.gamestore.demo.service.game;

import com.gamestore.demo.controller.dto.GameDto;
import com.gamestore.demo.exceptions.game.GameListEmptyException;
import com.gamestore.demo.exceptions.game.GameNotFoundException;
import com.gamestore.demo.mapper.GameMapper;
import com.gamestore.demo.model.Game;
import com.gamestore.demo.model.enums.Genre;
import com.gamestore.demo.repository.GameRepository;
import com.gamestore.demo.repository.PlatformRepository;
import com.gamestore.demo.repository.PublisherRepository;
import com.gamestore.demo.service.utils.GameUtils;
import com.gamestore.demo.service.validation.GameValidator;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Transactional
@Service
public class GameServiceImpl implements GameService {

    private final GameRepository gameRepository;
    private final PlatformRepository platformRepository;
    private final PublisherRepository publisherRepository;

    @Autowired
    public GameServiceImpl(GameRepository gameRepository, PlatformRepository platformRepository, PublisherRepository publisherRepository) {
        this.gameRepository = gameRepository;
        this.platformRepository = platformRepository;
        this.publisherRepository = publisherRepository;
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
    public Page<GameDto> getGamesBySinglePlayer(boolean singlePlayer, Pageable pageable) {
        Page<Game> games = gameRepository.findBySinglePlayer(singlePlayer, pageable);
        if (games.isEmpty()) {
            log.warn("No single player games found in the database");
            throw new GameListEmptyException("No single player games found in the database.");
        }
        Page<GameDto> gameDtoPage = games.map(GameMapper::toGameDto);
        log.info("Found {} single player games in the database.", gameDtoPage.getTotalElements());
        return gameDtoPage;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<GameDto> getGamesByMultiPlayer(boolean multiPlayer, Pageable pageable) {
        Page<Game> games = gameRepository.findByMultiPlayer(multiPlayer, pageable);
        if (games.isEmpty()) {
            log.warn("No multi player games found in the database");
            throw new GameListEmptyException("No multi player games found in the database.");
        }
        Page<GameDto> gameDtoPage = games.map(GameMapper::toGameDto);
        log.info("Found {} multi player games in the database.", gameDtoPage.getTotalElements());
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
        List<Game> validGames = games.stream().filter(GameValidator::isValidGame).toList();
        List<Game> savedGames = validGames.stream()
                .peek(game -> log.info("Saving new game with title {} to the database", game.getTitle()))
                .map(game -> {
                    GameUtils.setPlatforms(game, platformRepository);
                    GameUtils.setLastUpdated(game);
                    GameUtils.setPublisher(game, publisherRepository);
                    return GameUtils.saveGame(game, gameRepository, log);
                })
                .collect(Collectors.toList());

        log.info("Saved {} new games to the database", savedGames.size());
        return savedGames;
    }

    @Override
    @Transactional
    public Game updateGame(Long id, Game game) {
        log.info("Updating game with ID {} in the database", id);
        Game existingGame = gameRepository.findById(id).orElseThrow(() -> new GameNotFoundException(id));
        Hibernate.initialize(existingGame);
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

