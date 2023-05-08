package com.gamestore.demo.exceptions.publisher;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class PublisherNotFoundException extends RuntimeException{

    public PublisherNotFoundException(String name) {
        super("Could not find publisher with Name: " + name);
    }

    public PublisherNotFoundException(Long id) {
        super("Could not find publisher with ID: " + id);
    }
}
