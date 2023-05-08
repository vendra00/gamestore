package com.gamestore.demo.service.publisher;

import com.gamestore.demo.model.Publisher;

import java.util.List;

public interface PublisherService {
    List<Publisher> getAllPublishers();
    Publisher getPublisherById(Long id);
    Publisher savePublisher(Publisher publisher);
    Publisher updatePublisher(Long id, Publisher platform);
    void deletePublisherById(Long id);
}
