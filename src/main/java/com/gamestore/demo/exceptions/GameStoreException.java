package com.gamestore.demo.exceptions;

public class GameStoreException extends RuntimeException {
    public GameStoreException(String message) {
        super(message);
    }

    public GameStoreException(String message, Throwable cause) {
        super(message, cause);
    }
}
