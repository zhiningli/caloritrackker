package com.caloriplanner.calorimeter.clos.service;

import com.caloriplanner.calorimeter.clos.models.UserMeal;
import com.caloriplanner.calorimeter.clos.models.dto.MealDto;

import java.util.List;

public interface UserMealService {
    void createUserMeal(String userId, MealDto mealDto, double Weight);
    List<UserMeal> getUserMeals(String userId);
    void deleteUserMeal(String userId, String mealId);
}
