package com.gamestore.demo.service.user;

import com.gamestore.demo.exceptions.user.UserNotFoundException;
import com.gamestore.demo.model.Game;
import com.gamestore.demo.model.User;
import com.gamestore.demo.repository.UserRepository;
import com.gamestore.demo.service.utils.UserUtils;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserUtils userUtils;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, UserUtils userUtils) {
        log.debug("UserServiceImpl instantiated");
        this.userRepository = userRepository;
        this.userUtils = userUtils;
    }

    @Override
    @Transactional
    public User saveUser(User user) throws ValidationException {
        log.debug("saveUser called with user: {}", user);
        try {
            userUtils.prepareUserData(user);
            return userRepository.save(user);
        } catch (DataIntegrityViolationException e) {
            log.debug("User with email {} already exists", user.getEmail());
            throw new ValidationException("User with email " + user.getEmail() + " already exists.");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Page<User> getAllUsers(Pageable pageable) {
        log.debug("getAllUsers called with pageable: {}", pageable);
        return userRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> getUserById(Long id) {
        log.debug("getUserById called with id: {}", id);
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isEmpty()) {
            log.debug("User not found");
            throw new UserNotFoundException(id);
        }
        log.debug("User found");
        return optionalUser;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> getUserByEmail(String email) throws EntityNotFoundException {
        log.debug("getUserByEmail called with email: {}", email);
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isPresent()) {
            log.debug("User found");
            return optionalUser;
        } else {
            log.debug("User not found");
            throw new UserNotFoundException(email);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Game> getGamesByUserId(Long userId, Pageable pageable) {
        log.debug("getGamesByUserId called");
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        return new PageImpl<>(new ArrayList<>(user.getGames()), pageable, user.getGames().size());
    }

    @Override
    @Transactional
    public void addGameToUser(Long userId, Game game) {
        log.debug("addGameToUser called");
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            log.debug("User found");
            User user = optionalUser.get();
            user.getGames().add(game);
            userRepository.save(user);
        } else {
            log.debug("User not found");
            throw new UserNotFoundException(userId);
        }
    }

    @Override
    @Transactional
    public void removeGameFromUser(Long userId, Game game) throws UserNotFoundException {
        log.debug("removeGameFromUser called");
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            Set<Game> games = user.getGames();
            games.remove(game);
            userRepository.save(user);
        } else {
            throw new UserNotFoundException(userId);
        }
    }

    @Override
    @Transactional
    public void deleteUserById(Long id) throws UserNotFoundException {
        log.debug("deleteUserById called");
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            log.debug("User found");
            userRepository.deleteById(id);
        } else {
            log.debug("User not found");
            throw new UserNotFoundException(id);
        }
    }
}
