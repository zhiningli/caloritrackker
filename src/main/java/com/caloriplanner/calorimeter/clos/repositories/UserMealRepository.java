package com.caloriplanner.calorimeter.clos.repositories;

import com.caloriplanner.calorimeter.clos.models.UserMeal;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface UserMealRepository extends MongoRepository<UserMeal, String> {
    List<UserMeal> findByUserId(String userId);
    void deleteByUserIdAndMealId(String userId, String mealId);
}