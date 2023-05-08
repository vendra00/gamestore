package com.gamestore.demo.controller;

import com.gamestore.demo.model.Publisher;
import com.gamestore.demo.service.publisher.PublisherService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/publisher")
public class PublisherController {

    private final PublisherService publisherService;

    @Autowired
    public PublisherController(PublisherService publisherService) {
        this.publisherService = publisherService;
    }

    @GetMapping("")
    public ResponseEntity<List<Publisher>> getAllPublishers() {
        List<Publisher> publishers = publisherService.getAllPublishers();
        return new ResponseEntity<>(publishers, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Publisher> getPublisherById(@PathVariable Long id) {
        Publisher publisher = publisherService.getPublisherById(id);
        if (publisher == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(publisher, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<Publisher> savePublisher(@RequestBody Publisher publisher) {
        Publisher savedPublisher = publisherService.savePublisher(publisher);
        return new ResponseEntity<>(savedPublisher, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Publisher> updatePublisher(@PathVariable Long id, @Valid @RequestBody Publisher publisher) {
        Publisher updatedPublisher = publisherService.updatePublisher(id, publisher);
        return updatedPublisher != null ? new ResponseEntity<>(updatedPublisher, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePublisher(@PathVariable Long id) {
        publisherService.deletePublisherById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
