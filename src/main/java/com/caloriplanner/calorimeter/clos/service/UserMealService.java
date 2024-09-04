package com.caloriplanner.calorimeter.clos.service;

import com.caloriplanner.calorimeter.clos.models.User;
import com.caloriplanner.calorimeter.clos.models.UserMeal;
import com.caloriplanner.calorimeter.clos.models.dto.MealDto;
import com.caloriplanner.calorimeter.clos.models.dto.UserMealDto;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UserMealService {
    UserMealDto createUserMeal(String userSlug, MealDto mealDto);
    List<UserMealDto> getUserMeals(String userSlug);
    UserMealDto getUserMeal(String userSlug, MealDto mealDto);
    void updateUserMeal(String userSlug, MealDto mealDto);
    void deleteUserMeal(String userSlug, MealDto mealDto);

}
