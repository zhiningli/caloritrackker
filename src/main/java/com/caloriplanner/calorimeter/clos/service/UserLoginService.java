package com.caloriplanner.calorimeter.clos.service;

import com.caloriplanner.calorimeter.clos.models.User;

public interface UserLoginService {

    User registerUser(User user);
    User getUserByUsername(String username);
    boolean authenticateUser(String username, String password);
}
