package com.gamestore.demo.service.game.utils;

import com.gamestore.demo.model.Game;

public class GameUtils {
    public static void setEditedGameValues(Game source, Game target) {
        target.setTitle(source.getTitle());
        target.setDescription(source.getDescription());
        target.setPrice(source.getPrice());
        target.setGenre(source.getGenre());
        target.setReleaseDate((source.getReleaseDate()));
    }
}
