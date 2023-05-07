package com.gamestore.demo.repository;

import com.gamestore.demo.model.Game;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface GameRepository extends JpaRepository<Game, Long> {
    @Query("SELECT g FROM Game g JOIN g.platforms p WHERE p.name LIKE %:platformName%")
    Page<Game> findByPlatformName(@Param("platformName") String platformName, Pageable pageable);

}