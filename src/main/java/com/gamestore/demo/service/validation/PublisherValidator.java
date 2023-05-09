package com.gamestore.demo.service.validation;

import com.gamestore.demo.exceptions.publisher.InvalidPublisherException;
import com.gamestore.demo.model.Publisher;

public class PublisherValidator {
    public static void validatePublisher(Publisher publisher) {
        if (publisher == null) {
            throw new InvalidPublisherException("Publisher cannot be null");
        }
        if (publisher.getName() == null || publisher.getName().isEmpty()) {
            throw new InvalidPublisherException("Publisher name cannot be empty");
        }
        if (publisher.getCountry() == null) {
            throw new InvalidPublisherException("Publisher country cannot be empty");
        }
    }
}
