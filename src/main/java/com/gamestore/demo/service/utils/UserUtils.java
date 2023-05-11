package com.gamestore.demo.service.utils;

import com.gamestore.demo.model.User;
import com.gamestore.demo.service.validation.UserValidator;
import jakarta.validation.ValidationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserUtils {
    private final UserValidator userValidator;
    private final PasswordEncoder passwordEncoder;

    public UserUtils(UserValidator userValidator, PasswordEncoder passwordEncoder) {
        this.userValidator = userValidator;
        this.passwordEncoder = passwordEncoder;
    }

    public void prepareUserData(User user) throws ValidationException {
        userValidator.validate(user);
        String hashedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(hashedPassword);
    }
}

