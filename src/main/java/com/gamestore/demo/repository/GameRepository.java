package com.gamestore.demo.repository;

import com.gamestore.demo.model.Game;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameRepository extends JpaRepository<Game, Long> {
}
