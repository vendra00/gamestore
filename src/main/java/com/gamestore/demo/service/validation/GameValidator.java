package com.gamestore.demo.service.validation;

import com.gamestore.demo.model.Game;
import com.gamestore.demo.model.Platform;
import com.gamestore.demo.repository.PlatformRepository;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static com.gamestore.demo.service.validation.PlatformValidator.isValidPlatform;

public final class GameValidator {

    private GameValidator() {
        throw new IllegalStateException("Utility class");
    }

    public static boolean isValidGame(Game game) {
        return isValidGameTitle(game.getTitle()) && isValidGamePrice(BigDecimal.valueOf(game.getPrice()));
    }

    public static boolean isValidGameTitle(String title) {
        return title != null && !title.isEmpty();
    }

    public static boolean isValidGamePrice(BigDecimal price) {
        return price != null && price.compareTo(BigDecimal.ZERO) > 0;
    }

    public static Set<Platform> getValidPlatforms(Set<Platform> platforms, PlatformRepository platformRepository) {
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
