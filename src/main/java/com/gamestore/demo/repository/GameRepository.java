package com.gamestore.demo.repository;

import com.gamestore.demo.model.Game;
import com.gamestore.demo.model.enums.Genre;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface GameRepository extends JpaRepository<Game, Long> {
    @Query("SELECT g FROM Game g JOIN g.platforms p WHERE p.name LIKE %:platformName%")
    Page<Game> findByPlatformName(@Param("platformName") String platformName, Pageable pageable);
    @Query("SELECT g FROM Game g WHERE g.genre = :genre")
    Page<Game> findByGenre(@Param("genre") Genre genre, Pageable pageable);
    @Query("SELECT g FROM Game g WHERE YEAR(g.releaseDate) = :year")
    Page<Game> findByReleaseYear(@Param("year") int year, Pageable pageable);
    Page<Game> findByTitleContainingIgnoreCase(String title, Pageable pageable);
    Page<Game> findBySinglePlayer(boolean singlePlayer, Pageable pageable);
    Page<Game> findByMultiPlayer(boolean multiPlayer, Pageable pageable);
}