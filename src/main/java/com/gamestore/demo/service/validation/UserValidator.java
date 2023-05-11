package com.gamestore.demo.service.validation;

import com.gamestore.demo.model.User;
import jakarta.validation.ValidationException;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class UserValidator {

    public void validate(User user) throws ValidationException {
        validateUsername(user.getUsername());
        validateEmail(user.getEmail());
        validatePassword(user.getPassword());
    }

    private boolean isValidEmailFormat(String email) {
        String regex = "^[\\w-_.+]*[\\w-_.]@([\\w]+[.])+[\\w]+[\\w]$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    private void validateUsername(String username) throws ValidationException {
        if (username == null || username.isEmpty()) {
            throw new ValidationException("Username is required.");
        }
        if (username.length() > 30) {
            throw new ValidationException("Username must be less than or equal to 30 characters.");
        }
        // Check for any specific pattern or format
        // For example, enforce alphanumeric characters only
        if (!username.matches("^[a-zA-Z0-9]+$")) {
            throw new ValidationException("Username can only contain alphanumeric characters.");
        }
    }

    private void validateEmail(String email) throws ValidationException {
        if (email == null || email.isEmpty()) {
            throw new ValidationException("Email is required.");
        }
        if (!isValidEmailFormat(email)) {
            throw new ValidationException("Invalid email format.");
        }
    }

    private void validatePassword(String password) throws ValidationException {
        if (password == null || password.isEmpty()) {
            throw new ValidationException("Password is required.");
        }
        if (password.length() < 8) {
            throw new ValidationException("Password must have at least 8 characters.");
        }
        if (password.length() > 30) {
            throw new ValidationException("Password must be less than or equal to 30 characters.");
        }
        if (!password.matches(".*[A-Z].*")) {
            throw new ValidationException("Password must contain at least one uppercase letter.");
        }
        if (!password.matches(".*[a-z].*")) {
            throw new ValidationException("Password must contain at least one lowercase letter.");
        }
        if (!password.matches(".*\\d.*")) {
            throw new ValidationException("Password must contain at least one digit.");
        }
        if (!password.matches(".*[!@#$%^&*()].*")) {
            throw new ValidationException("Password must contain at least one special character.");
        }
    }


}

