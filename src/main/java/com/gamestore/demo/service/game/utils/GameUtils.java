package com.gamestore.demo.service.game.utils;

import com.gamestore.demo.exceptions.game.GameAlreadyExistsException;
import com.gamestore.demo.model.Game;
import com.gamestore.demo.model.Platform;
import com.gamestore.demo.model.Publisher;
import com.gamestore.demo.repository.GameRepository;
import com.gamestore.demo.repository.PlatformRepository;
import com.gamestore.demo.repository.PublisherRepository;
import com.gamestore.demo.service.validation.GameValidator;
import com.gamestore.demo.service.validation.PublisherValidator;
import org.slf4j.Logger;
import org.springframework.dao.DataIntegrityViolationException;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class GameUtils {
    public static void setEditedGameValues(Game source, Game target) {
        Field[] fields = Game.class.getDeclaredFields();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                Object value = field.get(source);
                if (value != null) {
                    field.set(target, value);
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    public static void setPlatforms(Game game, PlatformRepository platformRepository) {
        Set<Platform> selectedPlatforms = GameValidator.getValidPlatforms(game.getPlatforms(), platformRepository);
        game.setPlatforms(selectedPlatforms);
    }

    public static void setLastUpdated(Game game) {
        game.setLastUpdated(new Date());
    }

    public static void setPublisher(Game game, PublisherRepository publisherRepository) {
        Publisher publisher = game.getPublisher();
        if (publisher == null) {
            return;
        }

        Optional<Publisher> optionalPublisher = publisherRepository.findByName(publisher.getName());
        if (optionalPublisher.isPresent()) {
            addGameToPublisher(game, optionalPublisher.get());
            game.setPublisher(optionalPublisher.get());
        } else {
            PublisherValidator.validatePublisher(publisher);
            setGamesInPublisher(game, publisher);
            game.setPublisher(publisherRepository.save(publisher));
        }
    }


    private static void addGameToPublisher(Game game, Publisher publisher) {
        Set<Game> games = new HashSet<>(publisher.getGames());
        games.add(game);
        publisher.setGames(games);
    }

    private static void setGamesInPublisher(Game game, Publisher publisher) {
        publisher.setGames(Set.of(game));
    }

    public static Game saveGame(Game game, GameRepository gameRepository, Logger log) {
        try {
            Game savedGame = gameRepository.save(game);
            log.info("Saved game with ID {} and title {} to the database", savedGame.getId(), savedGame.getTitle());
            return savedGame;
        } catch (DataIntegrityViolationException e) {
            log.warn("Could not save game with title {} to the database because it already exists", game.getTitle());
            throw new GameAlreadyExistsException(game.getTitle());
        }
    }
}
