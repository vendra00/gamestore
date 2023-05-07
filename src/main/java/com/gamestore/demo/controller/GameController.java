package com.gamestore.demo.controller;

import com.gamestore.demo.controller.dto.GameDto;
import com.gamestore.demo.model.Game;
import com.gamestore.demo.model.enums.Genre;
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

    @GetMapping("/platform/{platformName}")
    public ResponseEntity<Page<GameDto>> getGamesByPlatformName(@PathVariable String platformName, @PageableDefault(size = 25) Pageable pageable) {
        Page<GameDto> games = gameService.getGamesByPlatformName(platformName, pageable);
        return new ResponseEntity<>(games, HttpStatus.OK);
    }

    @GetMapping("/genre/{genre}")
    public ResponseEntity<Page<GameDto>> getGamesByGenre(@PathVariable Genre genre, @PageableDefault(size = 25) Pageable pageable) {
        Page<GameDto> games = gameService.getGamesByGenre(genre, pageable);
        return new ResponseEntity<>(games, HttpStatus.OK);
    }

    @GetMapping("/year/{year}")
    public ResponseEntity<Page<GameDto>> getGamesByReleaseYear(@PathVariable int year, @PageableDefault(size = 25) Pageable pageable) {
        Page<GameDto> games = gameService.getGamesByReleaseYear(year, pageable);
        return new ResponseEntity<>(games, HttpStatus.OK);
    }

    @GetMapping("/title/{title}")
    public ResponseEntity<Page<GameDto>> getGamesByTitle(@PathVariable String title, @PageableDefault(size = 25) Pageable pageable) {
        Page<GameDto> games = gameService.getGamesByTitle(title, pageable);
        return new ResponseEntity<>(games, HttpStatus.OK);
    }

    @GetMapping("/singlePlayer/{singlePlayer}")
    public ResponseEntity<Page<GameDto>> getSinglePlayerGames(@PathVariable boolean singlePlayer, @PageableDefault(size = 25) Pageable pageable) {
        Page<GameDto> games = gameService.getGamesBySinglePlayer(singlePlayer, pageable);
        return new ResponseEntity<>(games, HttpStatus.OK);
    }

    @GetMapping("/multiPlayer/{multiPlayer}")
    public ResponseEntity<Page<GameDto>> getMultiPlayerGames(@PathVariable boolean multiPlayer, @PageableDefault(size = 25) Pageable pageable) {
        Page<GameDto> games = gameService.getGamesByMultiPlayer(multiPlayer, pageable);
        return new ResponseEntity<>(games, HttpStatus.OK);
    }

}

