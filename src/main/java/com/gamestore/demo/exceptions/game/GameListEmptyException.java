package com.gamestore.demo.exceptions.game;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class GameListEmptyException extends RuntimeException {
    public GameListEmptyException(String message) {
        super(message);
    }
}
