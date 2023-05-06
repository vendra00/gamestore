package com.gamestore.demo.repository.game;

import com.gamestore.demo.model.Game;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameRepository extends JpaRepository<Game, Long> {
}
