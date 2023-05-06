package com.gamestore.demo.exceptions.platform;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class PlatformNotFoundException extends RuntimeException {

    public PlatformNotFoundException(Long id) {
        super("Could not find platform with ID: " + id);
    }

}
