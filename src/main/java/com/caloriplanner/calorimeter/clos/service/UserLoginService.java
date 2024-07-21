package com.caloriplanner.calorimeter.clos.service;

import com.caloriplanner.calorimeter.clos.models.User;

public interface UserLoginService {

    User registerUser(User user);
    User getUserByUsername(String username);
    User getUserByEmail(String email);
    boolean authenticateUser(String identifier, String password);
}
