package com.caloriplanner.calorimeter.clos.service;

import com.caloriplanner.calorimeter.clos.models.User;

public interface UserService {

    public User registerUser(User user);
    public User getUserByUsername(String username);
    public boolean authenticateUser(String username, String password);
}
