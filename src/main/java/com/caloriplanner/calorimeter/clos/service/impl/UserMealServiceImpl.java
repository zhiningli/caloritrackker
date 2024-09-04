package com.caloriplanner.calorimeter.clos.service.impl;

import com.caloriplanner.calorimeter.clos.exceptions.ResourceNotFoundException;
import com.caloriplanner.calorimeter.clos.mapper.UserMealMapper;
import com.caloriplanner.calorimeter.clos.models.User;
import com.caloriplanner.calorimeter.clos.models.UserMeal;
import com.caloriplanner.calorimeter.clos.models.dto.MealDto;
import com.caloriplanner.calorimeter.clos.models.dto.UserMealDto;
import com.caloriplanner.calorimeter.clos.repositories.UserMealRepository;
import com.caloriplanner.calorimeter.clos.service.UserMealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserMealServiceImpl implements UserMealService {

    @Autowired
    private UserMealRepository userMealRepository;

    @Autowired
    private UserMealMapper userMealMapper;

    @Autowired
    private MealServiceImpl mealService;

    @Autowired
    private UserLoginServiceImpl userService;

    @Override
    @Transactional
    public UserMealDto createUserMeal(String userSlug, MealDto mealDto) {

        MealDto newMealDto = mealService.createMeal(mealDto);

        UserMeal userMeal = userMealMapper.mapToUserMeal(userSlug, newMealDto);

        userMealRepository.save(userMeal);
        return userMealMapper.mapToUserMealDto(userMeal);
    }

    @Override
    @Transactional
    public List<UserMealDto> getUserMeals(String userSlug) {
        User user = userService.getUserBySlug(userSlug);
        String userId = user.getId();

        List<UserMeal> userMeals = userMealRepository.findByUserId(userId);
        return userMeals.stream().map(userMealMapper::mapToUserMealDto).toList();
    }

    @Override
    @Transactional
    public UserMealDto getUserMeal(String userSlug, MealDto mealDto) throws ResourceNotFoundException{
        User user = userService.getUserBySlug(userSlug);
        String userId = user.getId();
        String mealId = mealDto.getId();

        UserMeal userMeal = userMealRepository.findByUserIdAndMealId(userId, mealId);

        return userMealMapper.mapToUserMealDto(userMeal);
    }


    @Override
    @Transactional
    public void updateUserMeal(String userSlug, MealDto mealDto) throws ResourceNotFoundException {
        User user = userService.getUserBySlug(userSlug);
        String userId = user.getId();
        String mealId = mealDto.getId();

        UserMeal userMeal = userMealRepository.findByUserIdAndMealId(userId, mealId);

        userMealRepository.delete(userMeal);
        mealService.deleteMealById(mealId);
        createUserMeal(userSlug, mealDto);
    }

    @Override
    public void deleteUserMeal(String userSlug, MealDto mealDto) {
        User user = userService.getUserBySlug(userSlug);
        String userId = user.getId();
        String mealId = mealDto.getId();

        UserMeal userMeal = userMealRepository.findByUserIdAndMealId(userId, mealId);
        userMealRepository.delete(userMeal);
        mealService.deleteMealById(mealId);
    }

}

