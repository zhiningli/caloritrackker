package com.caloriplanner.calorimeter.clos.service.impl;

import com.caloriplanner.calorimeter.clos.helpers.SlugHelper;
import com.caloriplanner.calorimeter.clos.models.User;
import com.caloriplanner.calorimeter.clos.models.dto.UserDto;
import com.caloriplanner.calorimeter.clos.repositories.UserRepository;
import com.caloriplanner.calorimeter.clos.service.UserLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserLoginServiceImpl implements UserLoginService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private SlugHelper slugHelper;

    @Override
    @Transactional
    public User registerUser(User user) {

        System.out.println("Raw password: " + user.getPassword());
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        System.out.println("Encoded password: " + encodedPassword);
        String baseSlug = slugHelper.generateSlug(user.getUsername());
        String uniqueSlug = slugHelper.ensureSlugUnique(baseSlug);
        user.setSlug(uniqueSlug);
        return userRepository.save(user);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean checkSlugExists(String slug){
        return slugHelper.checkSlugExists(slug);
    }

    @Override
    @Transactional(readOnly = true)
    public User getUserByUsername(String username) {
        User user = userRepository.findByUsername(username);
        System.out.println("User found with username"+username);
        return user;
    }

    @Override
    @Transactional(readOnly = true)
    public User getUserByEmail(String email){
        User user = userRepository.findByEmail(email);
        System.out.println("User found with email"+email);
        return user;
    }

    @Override
    @Transactional(readOnly = true)
    public boolean authenticateUser(String identifier, String password) {
        User user = userRepository.findByUsername(identifier);
        if (user == null) {
            user = userRepository.findByEmail(identifier);
        }

        if (user != null) {
            System.out.println("Found user with identifier: " + identifier);
            System.out.println("Raw password for login: " + password);
            System.out.println("Encoded password from DB: " + user.getPassword());

            boolean matches = passwordEncoder.matches(password, user.getPassword());
            System.out.println("Password matches: " + matches);

            return matches;
        } else {
            System.out.println("User not found with identifier: " + identifier);
            return false;
        }
    }
}


