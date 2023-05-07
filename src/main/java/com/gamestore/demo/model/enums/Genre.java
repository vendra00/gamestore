package com.gamestore.demo.model.enums;

import java.util.Arrays;
import java.util.List;

public enum Genre {
    ACTION,
    ADVENTURE,
    RPG,
    STRATEGY,
    SPORTS,
    SIMULATION,
    PUZZLE,
    HORROR,
    FPS,
    PLATFORMER,
    RACING,
    FIGHTING,
    SURVIVAL;

    public static List<Genre> getAllGenres() {
        return Arrays.asList(Genre.values());
    }
}

