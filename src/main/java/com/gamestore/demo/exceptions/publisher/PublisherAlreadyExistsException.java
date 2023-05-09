package com.gamestore.demo.exceptions.publisher;

public class PublisherAlreadyExistsException extends RuntimeException {
    public PublisherAlreadyExistsException(String name) {
        super("Publisher with name '" + name + "' already exists.");
    }
}
