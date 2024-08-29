package com.caloriplanner.calorimeter.clos.controllers;

import com.caloriplanner.calorimeter.clos.helpers.JwtUtil;
import com.caloriplanner.calorimeter.clos.models.User;
import com.caloriplanner.calorimeter.clos.service.UserLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserLoginService userLoginService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody User user) {
        User registeredUser = userLoginService.registerUser(user);
        return new ResponseEntity<>(registeredUser, HttpStatus.CREATED);
    }

    @GetMapping("/{username}")
    public ResponseEntity<User> getUserByUsername(@PathVariable String username) {
        User user = userLoginService.getUserByUsername(username);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody Map<String, String> credentials) {
        String identifier = credentials.get("identifier");
        String password = credentials.get("password");

        boolean isAuthenticated = userLoginService.authenticateUser(identifier, password);
        System.out.println("The authentication has been successful?" + identifier);

        if (isAuthenticated) {
            // Try to retrieve the user by username or email
            System.out.println("The identifier is" + identifier);
            User user = userLoginService.getUserByUsername(identifier);
            if (user == null) {
                System.out.println("The identifier is not a username, searching for email" + identifier);
                user = userLoginService.getUserByEmail(identifier);
                System.out.println("Let me see how does this look like" + user);
            }

            // Ensure user is not null before proceeding
            if (user != null) {
                String token = jwtUtil.generateToken(user);
                System.out.println("User found: " + user.getUsername() + ", Slug: " + user.getSlug());

                // Include the slug in the response
                return ResponseEntity.ok(Map.of(
                        "Token", token,
                        "username", user.getUsername(),
                        "slug", user.getSlug()
                ));
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not found after authentication");
            }
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }

    @GetMapping("/check-slug/{slug}")
    public ResponseEntity<Boolean> checkSlugExists(@PathVariable String slug) {
        boolean exists = userLoginService.checkSlugExists(slug);
        return new ResponseEntity<>(exists, HttpStatus.OK);
    }
}
