package com.gamestore.demo.service.user;

import com.gamestore.demo.model.Game;
import com.gamestore.demo.model.User;
import jakarta.validation.ValidationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface UserService  {
    @Transactional
    User saveUser(User user) throws ValidationException;

    // Returns a page of all users, sorted by email address
    Page<User> getAllUsers(Pageable pageable);

    // Returns an Optional containing the user with the given ID, or an empty Optional if not found
    Optional<User> getUserById(Long id);

    // Returns an Optional containing the user with the given email address, or an empty Optional if not found
    Optional<User> getUserByEmail(String email);

    // Returns a page of all games for the user with the given ID, sorted by title
    Page<Game> getGamesByUserId(Long userId, Pageable pageable);

    // Adds the given game to the user's collection of games
    void addGameToUser(Long userId, Game game);

    // Removes the given game from the user's collection of games
    void removeGameFromUser(Long userId, Game game);

    // Deletes the user with the given ID
    void deleteUserById(Long id);

}

