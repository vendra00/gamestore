package com.gamestore.demo.service.game;

import com.gamestore.demo.controller.dto.GameDto;
import com.gamestore.demo.controller.dto.PlatformDto;
import com.gamestore.demo.exceptions.game.GameAlreadyExistsException;
import com.gamestore.demo.exceptions.game.GameListEmptyException;
import com.gamestore.demo.exceptions.game.GameNotFoundException;
import com.gamestore.demo.mapper.GameMapper;
import com.gamestore.demo.model.Game;
import com.gamestore.demo.model.Platform;
import com.gamestore.demo.repository.GameRepository;
import com.gamestore.demo.repository.PlatformRepository;
import com.gamestore.demo.service.validation.GameValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
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
            throw new GameListEmptyException();
        }
        Page<GameDto> gameDtoPage = gamePage.map(game -> new GameDto(
                game.getTitle(),
                game.getDescription(),
                game.getPrice(),
                game.getPlatforms().stream()
                        .map(platform -> new PlatformDto(
                                platform.getName(),
                                platform.getDescription()
                        ))
                        .collect(Collectors.toSet())
        ));
        log.info("Found {} games in the database.", gameDtoPage.getTotalElements());
        return gameDtoPage;
    }


    @Override
    public List<GameDto> getGamesByPlatformName(String platformName) {
        List<Game> games = gameRepository.findByPlatformName(platformName);
        return games.stream()
                .map(GameMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public GameDto getGameById(Long id) {
        log.info("Retrieving game with ID {} from the database", id);
        Game game = gameRepository.findById(id).orElseThrow(() -> new GameNotFoundException(id));
        Set<PlatformDto> platformDtos = game.getPlatforms().stream()
                .map(platform -> new PlatformDto(
                        platform.getName(),
                        platform.getDescription()
                ))
                .collect(Collectors.toSet());
        return new GameDto(game.getTitle(), game.getDescription(), game.getPrice(), platformDtos);
    }

    @Override
    public List<Game> saveGame(List<Game> games) {
        List<Game> savedGames = new ArrayList<>();
        for (Game game : games) {
            if (!GameValidator.isValidGame(game)) {
                continue;
            }
            log.info("Saving new game with title {} to the database", game.getTitle());
            Set<Platform> selectedPlatforms = GameValidator.getValidPlatforms(game.getPlatforms(), platformRepository);
            game.setPlatforms(selectedPlatforms);
            try {
                savedGames.add(gameRepository.save(game));
            } catch (DataIntegrityViolationException e) {
                log.warn("Could not save game with title {} to the database because it already exists", game.getTitle());
                throw new GameAlreadyExistsException(game.getTitle());
            }
        }
        List<Game> result = gameRepository.saveAll(savedGames);
        log.info("Saved {} new games to the database", result.size());
        return result;
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

