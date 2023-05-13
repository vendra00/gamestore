package com.gamestore.demo.service.validation;

import com.gamestore.demo.exceptions.publisher.InvalidPublisherException;
import com.gamestore.demo.model.Publisher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class PublisherValidator {
    public void validatePublisher(Publisher publisher) {
        log.debug("validatePublisher called with publisher: {}", publisher);
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
