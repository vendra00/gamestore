package com.gamestore.demo.controller;

import com.gamestore.demo.model.Game;
import com.gamestore.demo.model.dto.GameDto;
import com.gamestore.demo.repository.game.GameService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/games")
public class GameController {

    private final GameService gameService;

    @Autowired
    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @GetMapping("")
    public ResponseEntity<List<GameDto>> getAllGames() {
        List<GameDto> games = gameService.getAllGames();
        return new ResponseEntity<>(games, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GameDto> getGameById(@PathVariable Long id) {
        GameDto game = gameService.getGameById(id);
        if (game == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(game, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<Game> saveGame(@RequestBody Game gameDto) {
        Game savedGame = gameService.saveGame(gameDto);
        return new ResponseEntity<>(savedGame, HttpStatus.CREATED);
    }


    @PutMapping("/{id}")
    public ResponseEntity<Game> update(@PathVariable Long id, @Valid @RequestBody Game game) {
        Game updatedGame = gameService.updateGame(id, game);
        return updatedGame != null ? new ResponseEntity<>(updatedGame, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGame(@PathVariable Long id) {
        gameService.deleteGameById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}

