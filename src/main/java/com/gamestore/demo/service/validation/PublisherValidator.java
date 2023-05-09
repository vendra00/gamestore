package com.gamestore.demo.service.validation;

import com.gamestore.demo.exceptions.publisher.InvalidPublisherException;
import com.gamestore.demo.model.Publisher;

public class PublisherValidator {
    public static void validatePublisher(Publisher publisher) {
        if (publisher.getName() == null || publisher.getName().trim().isEmpty()) {
            throw new InvalidPublisherException("Publisher name cannot be null or empty.");
        }
    }
}
