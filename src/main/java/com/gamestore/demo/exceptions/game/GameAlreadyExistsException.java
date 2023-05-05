package com.gamestore.demo.exceptions.game;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class GameAlreadyExistsException extends RuntimeException {
    public GameAlreadyExistsException(String title) {
        super("Game with title '" + title + "' already exists.");
    }
}

