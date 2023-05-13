package com.gamestore.demo.service.utils;

import com.gamestore.demo.exceptions.game.GameAlreadyExistsException;
import com.gamestore.demo.model.Game;
import com.gamestore.demo.model.Platform;
import com.gamestore.demo.model.Publisher;
import com.gamestore.demo.repository.GameRepository;
import com.gamestore.demo.repository.PlatformRepository;
import com.gamestore.demo.repository.PublisherRepository;
import com.gamestore.demo.service.validation.GameValidator;
import com.gamestore.demo.service.validation.PublisherValidator;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Component
public class GameUtils {

    private final GameRepository gameRepository;
    private final GameValidator gameValidator;
    private final PublisherValidator publisherValidator;

    @Autowired
    public GameUtils(GameRepository gameRepository, GameValidator gameValidator, PublisherValidator publisherValidator) {
        this.gameRepository = gameRepository;
        this.gameValidator = gameValidator;
        this.publisherValidator = publisherValidator;
    }

    public void setEditedGameValues(Game source, Game target) {
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

    public void setPlatforms(Game game, PlatformRepository platformRepository) {
        Set<Platform> selectedPlatforms = gameValidator.getValidPlatforms(game.getPlatforms(), platformRepository);
        game.setPlatforms(selectedPlatforms);
    }

    public void setLastUpdated(Game game) {
        game.setLastUpdated(new Date());
    }

    public void setPublisher(Game game, PublisherRepository publisherRepository) {
        Publisher publisher = game.getPublisher();
        if (publisher == null) {
            return;
        }

        Optional<Publisher> optionalPublisher = publisherRepository.findByName(publisher.getName());
        if (optionalPublisher.isPresent()) {
            addGameToPublisher(game, optionalPublisher.get());
            game.setPublisher(optionalPublisher.get());
        } else {
            publisherValidator.validatePublisher(publisher);
            setGamesInPublisher(game, publisher);
            game.setPublisher(publisherRepository.save(publisher));
        }
    }


    private void addGameToPublisher(Game game, Publisher publisher) {
        Set<Game> games = new HashSet<>(publisher.getGames());
        games.add(game);
        publisher.setGames(games);
    }

    private void setGamesInPublisher(Game game, Publisher publisher) {
        publisher.setGames(Set.of(game));
    }

    public Game saveGame(Game game, GameRepository gameRepository, Logger log) {
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
