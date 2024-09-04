package com.caloriplanner.calorimeter.clos.mapper;


import com.caloriplanner.calorimeter.clos.exceptions.ResourceNotFoundException;
import com.caloriplanner.calorimeter.clos.models.UserMeal;
import com.caloriplanner.calorimeter.clos.models.User;
import com.caloriplanner.calorimeter.clos.models.dto.MealDto;
import com.caloriplanner.calorimeter.clos.models.dto.UserMealDto;
import com.caloriplanner.calorimeter.clos.models.Meal;
import com.caloriplanner.calorimeter.clos.repositories.MealRepository;
import com.caloriplanner.calorimeter.clos.repositories.UserRepository;
import com.caloriplanner.calorimeter.clos.service.impl.MealServiceImpl;
import com.caloriplanner.calorimeter.clos.service.impl.UserLoginServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class UserMealMapper {

    @Autowired
    UserLoginServiceImpl userService;

    @Autowired
    MealServiceImpl mealService;

    public UserMealDto mapToUserMealDto(UserMeal userMeal){

        String mealID = userMeal.getMealId();
        MealDto mealDto = mealService.getMealById(mealID);

        String userID = userMeal.getUserId();
        User user = userService.getUserById(userID);

        String userSlug = user.getSlug();

        return  UserMealDto.builder()
                .id(userMeal.getId())
                .mealDto(mealDto)
                .userSlug(userSlug)
                .build();
    }

    public UserMeal mapToUserMeal(UserMealDto userMealDto){
        MealDto mealDto = userMealDto.getMealDto();
        String mealID = mealDto.getId();

        String userSlug = userMealDto.getUserSlug();
        User user = userService.getUserBySlug(userSlug);
        String userID = user.getId();

        return UserMeal.builder()
                .id(userMealDto.getId() != null ? userMealDto.getId() : UUID.randomUUID().toString())
                .mealId(mealID)
                .userId(userID)
                .build();
    }

    public UserMeal mapToUserMeal(String userSlug, MealDto mealDto){

        String mealID = mealDto.getId();

        User user = userService.getUserBySlug(userSlug);
        String userID = user.getId();

        return UserMeal.builder()
                .id(UUID.randomUUID().toString())
                .mealId(mealID)
                .userId(userID)
                .build();
    }
}
