package com.gamestore.demo.service.validation;

import com.gamestore.demo.exceptions.game.GameListEmptyException;
import com.gamestore.demo.model.Game;
import com.gamestore.demo.model.Platform;
import com.gamestore.demo.model.Publisher;
import com.gamestore.demo.model.enums.Genre;
import com.gamestore.demo.repository.PlatformRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static com.gamestore.demo.service.validation.PlatformValidator.isValidPlatform;

@Slf4j
@Component
public class GameValidator {

    private GameValidator() {
    }

    public boolean isValidGame(Game game) {
        return isValidGameTitle(game.getTitle())
                && isValidGamePrice(BigDecimal.valueOf(game.getPrice()))
                && isValidGameGenre(game.getGenre())
                && isValidGameReleaseDate(game.getReleaseDate());
    }

    public boolean isValidGameTitle(String title) {
        return title != null && !title.isEmpty();
    }

    public boolean isValidGamePrice(BigDecimal price) {
        return price != null && price.compareTo(BigDecimal.ZERO) > 0;
    }

    public boolean isValidGameGenre(Genre genre) {
        return genre != null;
    }

    public void isGameEmpty(Page<Game> gamePage) {
        if (gamePage.isEmpty()) {
            log.warn("No games found in the database.");
            throw new GameListEmptyException("No games found in the database.");
        }
    }

    public boolean isValidGameReleaseDate(Date releaseDate) {
        return releaseDate != null;
    }

    private boolean isValidPublisher(Publisher publisher) {
        return publisher != null && publisher.getName() != null && publisher.getCountry() != null;
    }


    public Set<Platform> getValidPlatforms(Set<Platform> platforms, PlatformRepository platformRepository) {
        Set<Platform> validPlatforms = new HashSet<>();
        for (Platform platform : platforms) {
            if (isValidPlatform(platform)) {
                Optional<Platform> optionalPlatform = platformRepository.findByName(platform.getName());
                if (optionalPlatform.isPresent()) {
                    validPlatforms.add(optionalPlatform.get());
                } else {
                    Platform savedPlatform = platformRepository.save(platform);
                    validPlatforms.add(savedPlatform);
                }
            }
        }
        return validPlatforms;
    }


}
