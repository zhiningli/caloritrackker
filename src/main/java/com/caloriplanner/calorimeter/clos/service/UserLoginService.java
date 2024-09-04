package com.caloriplanner.calorimeter.clos.service;

import com.caloriplanner.calorimeter.clos.models.User;
import com.caloriplanner.calorimeter.clos.models.dto.UserDto;

public interface UserLoginService {

    User registerUser(User user);
    User getUserByUsername(String username);
    User getUserByEmail(String email);
    User getUserBySlug(String slug);
    boolean authenticateUser(String identifier, String password);
    boolean checkSlugExists(String slug);
}
