package com.gamestore.demo.repository.game;

import com.gamestore.demo.model.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface GameRepository extends JpaRepository<Game, Long> {
    @Query("SELECT g FROM Game g JOIN g.platforms p WHERE p.name = :platformName")
    List<Game> findByPlatformName(@Param("platformName") String platformName);
}
