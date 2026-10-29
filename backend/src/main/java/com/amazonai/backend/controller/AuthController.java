
package com.amazonai.backend.controller;

import com.amazonai.backend.entity.User;
import com.amazonai.backend.repository.UserRepository;
import com.amazonai.backend.service.JwtService;
import com.amazonai.backend.service.UserService;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:5173")
public class AuthController {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;

    public AuthController(
            UserRepository userRepository,
            JwtService jwtService,
            PasswordEncoder passwordEncoder,
            UserService userService) {

        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
    }

    // Register a new user through the shared service
    @PostMapping("/register")
    public Map<String, String> register(@RequestBody User user) {

        userService.registerUser(user);

        return Map.of(
                "message",
                "Account created successfully. Please log in."
        );
    }

    // Log in an existing user
    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody User user) {

        if (user.getEmail() == null || user.getPassword() == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Email and password are required."
            );
        }

        String email = user.getEmail().trim().toLowerCase();
        User existingUser = userRepository.findByEmail(email);

        if (existingUser == null) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED,
                    "Invalid email or password."
            );
        }

        String storedPassword = existingUser.getPassword();
        String enteredPassword = user.getPassword();

        boolean passwordMatches;

        if (storedPassword != null
                && (storedPassword.startsWith("$2a$")
                || storedPassword.startsWith("$2b$")
                || storedPassword.startsWith("$2y$"))) {

            passwordMatches = passwordEncoder.matches(
                    enteredPassword, storedPassword);

        } else {
            // Compatibility with accounts created before BCrypt.
            passwordMatches = storedPassword != null
                    && storedPassword.equals(enteredPassword);

            if (passwordMatches) {
                existingUser.setPassword(
                        passwordEncoder.encode(enteredPassword));
                userRepository.save(existingUser);
            }
        }

        if (!passwordMatches) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED,
                    "Invalid email or password."
            );
        }

        String token = jwtService.generateToken(existingUser.getEmail());

        return Map.of(
                "token", token,
                "userId", existingUser.getId(),
                "name", existingUser.getName(),
                "role", existingUser.getRole()
        );
    }
}
