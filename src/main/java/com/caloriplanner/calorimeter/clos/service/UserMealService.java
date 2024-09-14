package com.caloriplanner.calorimeter.clos.service;

import com.caloriplanner.calorimeter.clos.models.User;
import com.caloriplanner.calorimeter.clos.models.UserMeal;
import com.caloriplanner.calorimeter.clos.models.dto.MealDto;
import com.caloriplanner.calorimeter.clos.models.dto.UserMealDto;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UserMealService {
    UserMealDto createUserMeal(String userSlug, MealDto mealDto);
    List<UserMealDto> createUserMeals(String userSlug, List<MealDto> mealDtoList);
    List<UserMealDto> getUserMeals(String userSlug);
    UserMealDto getUserMeal(String userSlug, MealDto mealDto);
    UserMealDto updateUserMeal(String userSlug, MealDto mealDto);
    List<UserMealDto> updateUserMeals(String userSlug, List<MealDto> mealDtoList);
    void deleteUserMeal(String userSlug, MealDto mealDto);

}
