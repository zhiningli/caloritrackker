package com.caloriplanner.calorimeter.clos.mapper;


import com.caloriplanner.calorimeter.clos.exceptions.ResourceNotFoundException;
import com.caloriplanner.calorimeter.clos.models.UserMeal;
import com.caloriplanner.calorimeter.clos.models.User;
import com.caloriplanner.calorimeter.clos.models.dto.MealDto;
import com.caloriplanner.calorimeter.clos.models.dto.UserMealDto;
import com.caloriplanner.calorimeter.clos.models.Meal;
import com.caloriplanner.calorimeter.clos.repositories.MealRepository;
import com.caloriplanner.calorimeter.clos.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class UserMealMapper {

    @Autowired
    UserRepository userRepository;

    @Autowired
    MealRepository mealRepository;

    @Autowired
    MealMapper mealMapper;

    public UserMealDto mapToUserMealDto(UserMeal userMeal){

        String mealID = userMeal.getMealId();
        Meal meal = mealRepository.findById(mealID).orElseThrow(
                () -> new ResourceNotFoundException("No meal with id "+mealID+" found!"));
        MealDto mealDto= mealMapper.mapToMealDto(meal);

        String userID = userMeal.getUserId();
        User user = userRepository.findById(userID).orElseThrow(
                () -> new ResourceNotFoundException("No user with id "+userID+" found!"));

        String userSlug = user.getSlug();

        return  UserMealDto.builder()
                .id(userMeal.getId())
                .mealDto(mealDto)
                .userSlug(userSlug)
                .build();
    }

    public UserMeal mapToUserMeal(UserMealDto userMealDto){
        MealDto mealDto = userMealDto.getMealDto();
        Meal meal = mealMapper.mapToMeal(mealDto);
        String mealID = meal.getId();

        String userSlug = userMealDto.getUserSlug();
        User user = userRepository.findBySlug(userSlug);
        String userID = user.getId();

        return UserMeal.builder()
                .id(userMealDto.getId() != null ? userMealDto.getId() : UUID.randomUUID().toString())
                .mealId(mealID)
                .userId(userID)
                .build();
    }

    public UserMeal mapToUserMeal(String userSlug, MealDto mealDto){

        String mealID = mealDto.getId();

        User user = userRepository.findBySlug(userSlug);
        String userID = user.getId();

        return UserMeal.builder()
                .id(UUID.randomUUID().toString())
                .mealId(mealID)
                .userId(userID)
                .build();
    }
}
