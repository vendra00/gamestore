package com.gamestore.demo.service.publisher;

import com.gamestore.demo.exceptions.publisher.PublisherNotFoundException;
import com.gamestore.demo.model.Publisher;
import com.gamestore.demo.repository.PublisherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PublisherServiceImpl implements PublisherService {

    private final PublisherRepository publisherRepository;

    @Autowired
    public PublisherServiceImpl(PublisherRepository publisherRepository) {
        this.publisherRepository = publisherRepository;
    }

    @Override
    public List<Publisher> getAllPublishers() {
        return publisherRepository.findAll();
    }

    @Override
    public Publisher getPublisherById(Long id) {
        return publisherRepository.findById(id).orElseThrow(() -> new PublisherNotFoundException(id));
    }

    @Override
    public Publisher savePublisher(Publisher publisher) {return publisherRepository.save(publisher); }

    @Override
    public Publisher updatePublisher(Long id, Publisher platform) {
        Optional<Publisher> optionalPublisher = publisherRepository.findById(id);
        if (optionalPublisher.isPresent()) {
            Publisher existingPublisher = optionalPublisher.get();
            existingPublisher.setName(platform.getName());
            return publisherRepository.save(existingPublisher);
        } else {
            throw new PublisherNotFoundException(id);
        }
    }

    @Override
    public void deletePublisherById(Long id) {
        Optional<Publisher> optionalPublisher = publisherRepository.findById(id);
        if (optionalPublisher.isPresent()) {
            publisherRepository.deleteById(id);
        } else {
            throw new PublisherNotFoundException(id);
        }
    }
}
