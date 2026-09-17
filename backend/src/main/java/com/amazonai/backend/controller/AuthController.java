package com.amazonai.backend.controller;

import org.springframework.web.bind.annotation.*;

import com.amazonai.backend.entity.User;
import com.amazonai.backend.repository.UserRepository;
import com.amazonai.backend.service.JwtService;

import java.util.Map;


@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:5173")
public class AuthController {


    private final UserRepository userRepository;
    private final JwtService jwtService;


    public AuthController(UserRepository userRepository,
                          JwtService jwtService) {

        this.userRepository = userRepository;
        this.jwtService = jwtService;

    }



    // Register User
    @PostMapping("/register")
    public User register(@RequestBody User user) {

        return userRepository.save(user);

    }



    // Login User
    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody User user) {


        User existingUser =
                userRepository.findByEmail(user.getEmail());
                System.out.println("Email received: " + user.getEmail());
System.out.println("Password received: " + user.getPassword());

if(existingUser != null){
    System.out.println("DB Password: " + existingUser.getPassword());
}


        if(existingUser != null &&
           existingUser.getPassword()
           .equals(user.getPassword())) {


            String token =
                    jwtService.generateToken(
                            existingUser.getEmail()
                    );


            return Map.of(
                    "token", token,
                    "userId", existingUser.getId(),
                    "name", existingUser.getName(),
                    "role", existingUser.getRole()
            );

        }


        return Map.of(
                "message",
                "Invalid email or password"
        );

    }

}