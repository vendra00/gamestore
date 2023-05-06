package com.gamestore.demo.controller;

import com.gamestore.demo.model.Platform;
import com.gamestore.demo.service.platform.PlatformService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/platforms")
public class PlatformController {

    private final PlatformService platformService;

    @Autowired
    public PlatformController(PlatformService platformService) {
        this.platformService = platformService;
    }

    @GetMapping("")
    public ResponseEntity<List<Platform>> getAllPlatforms() {
        List<Platform> platforms = platformService.getAllPlatforms();
        return new ResponseEntity<>(platforms, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Platform> getPlatformById(@PathVariable Long id) {
        Platform platform = platformService.getPlatformById(id);
        if (platform == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(platform, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<Platform> savePlatform(@RequestBody Platform platform) {
        Platform savedPlatform = platformService.savePlatform(platform);
        return new ResponseEntity<>(savedPlatform, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Platform> updatePlatform(@PathVariable Long id, @Valid @RequestBody Platform platform) {
        Platform updatedPlatform = platformService.updatePlatform(id, platform);
        return updatedPlatform != null ? new ResponseEntity<>(updatedPlatform, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePlatform(@PathVariable Long id) {
        platformService.deletePlatformById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}

