package com.gamestore.demo.controller;

import com.gamestore.demo.exceptions.game.GameListEmptyException;
import com.gamestore.demo.model.Game;
import com.gamestore.demo.controller.dto.GameDto;
import com.gamestore.demo.service.game.GameService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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
    public ResponseEntity<Page<GameDto>> getAllGames(@PageableDefault(size = 25) Pageable pageable) {
        Page<GameDto> games = gameService.getAllGames(pageable);
        if (games.isEmpty()) {
            throw new GameListEmptyException();
        }
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
    public ResponseEntity<List<Game>> saveGame(@RequestBody List<Game> gameDtoList) {
        List<Game> savedGame = gameService.saveGame(gameDtoList);
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

    @GetMapping(params = "platform")
    public ResponseEntity<List<GameDto>> getGamesByPlatformName(@RequestParam("platform") String platformName) {
        List<GameDto> games = gameService.getGamesByPlatformName(platformName);
        return new ResponseEntity<>(games, HttpStatus.OK);
    }
}

