package com.caloriplanner.calorimeter.clos.service.impl;

import com.caloriplanner.calorimeter.clos.mapper.MealMapper;
import com.caloriplanner.calorimeter.clos.models.Meal;
import com.caloriplanner.calorimeter.clos.models.UserMeal;
import com.caloriplanner.calorimeter.clos.models.dto.MealDto;
import com.caloriplanner.calorimeter.clos.repositories.MealRepository;
import com.caloriplanner.calorimeter.clos.repositories.UserMealRepository;
import com.caloriplanner.calorimeter.clos.service.UserMealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserMealServiceImpl implements UserMealService {

    @Autowired
    private MealRepository mealRepository;

    @Autowired
    private UserMealRepository userMealRepository;

    @Autowired
    private MealMapper mealMapper;

    @Override
    @Transactional
    public void createUserMeal(String userId, MealDto mealDto, double weight) {
        Meal meal = mealRepository.findByName(mealDto.getName());

        if (meal == null) {
            meal = mealMapper.mapToMeal(mealDto);
            meal = mealRepository.save(meal);
        }

        UserMeal userMeal = UserMeal.builder()
                .meal(meal)
                .userId(userId)
                .build();

        userMealRepository.save(userMeal);
    }

    @Override
    @Transactional
    public List<UserMeal> getUserMeals(String userId) {
        return userMealRepository.findByUserId(userId);
    }

    @Override
    @Transactional
    public void deleteUserMeal(String userId, String mealId) {
        userMealRepository.deleteByUserIdAndMealId(userId, mealId);
    }
}

