package com.gamestore.demo.repository.game;

import com.gamestore.demo.exceptions.game.GameAlreadyExistsException;
import com.gamestore.demo.exceptions.game.GameListEmptyException;
import com.gamestore.demo.exceptions.game.GameNotFoundException;
import com.gamestore.demo.model.Game;
import com.gamestore.demo.model.Platform;
import com.gamestore.demo.model.dto.GameDto;
import com.gamestore.demo.model.dto.PlatformDto;
import com.gamestore.demo.repository.platform.PlatformRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
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
    public List<GameDto> getAllGames() {
        log.info("Retrieving all games from the database");
        List<GameDto> gameDTOs = gameRepository.findAll().stream()
                .map(game -> new GameDto(
                        game.getId(),
                        game.getTitle(),
                        game.getDescription(),
                        game.getPrice(),
                        game.getPlatforms().stream()
                                .map(platform -> new PlatformDto(
                                        platform.getId(),
                                        platform.getName(),
                                        platform.getDescription()
                                ))
                                .collect(Collectors.toSet())
                ))
                .collect(Collectors.toList());
        if (gameDTOs.isEmpty()) {
            throw new GameListEmptyException();
        }
        log.info("Found {} games in the database", gameDTOs.size());
        return gameDTOs;
    }

    @Override
    @Transactional(readOnly = true)
    public GameDto getGameById(Long id) {
        log.info("Retrieving game with ID {} from the database", id);
        Game game = gameRepository.findById(id).orElseThrow(() -> new GameNotFoundException(id));
        Set<PlatformDto> platformDtos = game.getPlatforms().stream()
                .map(platform -> new PlatformDto(
                        platform.getId(),
                        platform.getName(),
                        platform.getDescription()
                ))
                .collect(Collectors.toSet());
        return new GameDto(game.getId(), game.getTitle(), game.getDescription(), game.getPrice(), platformDtos);
    }

    @Override
    public Game saveGame(Game game) {
        log.info("Saving new game with title {} to the database", game.getTitle());
        try {
            Set<Platform> selectedPlatforms = new HashSet<>();
            for (Platform platform : game.getPlatforms()) {
                Optional<Platform> optionalPlatform = platformRepository.findByName(platform.getName());
                if (optionalPlatform.isPresent()) {
                    selectedPlatforms.add(optionalPlatform.get());
                } else {
                    Platform savedPlatform = platformRepository.save(platform);
                    selectedPlatforms.add(savedPlatform);
                }
            }
            game.setPlatforms(selectedPlatforms);
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

