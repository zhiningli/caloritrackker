package com.caloriplanner.calorimeter.clos.repositories;

import com.caloriplanner.calorimeter.clos.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {
    User findByUsername(String username);
    User findByEmail(String email);
}
