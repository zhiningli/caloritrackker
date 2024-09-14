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
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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
    public List<UserMealDto> createUserMeals(String userSlug, List<MealDto> mealDtoList) {
        List<UserMeal> userMealsToSave = new ArrayList<>();
        List<UserMealDto> savedUserMealDtoList = new ArrayList<>();
        try {
            for (MealDto mealDto : mealDtoList) {
                System.out.println("mealDto extracted from the mealDtoList"+mealDto);
                MealDto newMealDto = mealService.createMeal(mealDto);
                UserMeal userMeal = userMealMapper.mapToUserMeal(userSlug, newMealDto);
                userMealsToSave.add(userMeal);
            }
            List<UserMeal> savedUserMeals = userMealRepository.saveAll(userMealsToSave);
            for (UserMeal savedUserMeal : savedUserMeals) {
                savedUserMealDtoList.add(userMealMapper.mapToUserMealDto(savedUserMeal));
            }

            return savedUserMealDtoList;

        } catch (DataAccessException e) {
            throw new RuntimeException("Error occurred while saving meals: " + e.getMessage(), e);
        } catch (Exception e) {
            throw new RuntimeException("Unexpected error occurred: " + e.getMessage(), e);
        }
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
    public UserMealDto updateUserMeal(String userSlug, MealDto mealDto) throws ResourceNotFoundException {
        User user = userService.getUserBySlug(userSlug);
        String userId = user.getId();
        String mealId = mealDto.getId();

        // Obtain the right userMeal from the repository and delete it
        UserMeal userMeal = userMealRepository.findByUserIdAndMealId(userId, mealId);
        userMealRepository.delete(userMeal);

        // Update meal in mealRepository as well and create a newUserMeal, save it to the repository
        MealDto newMealDto = mealService.updateMeal(mealDto);
        UserMeal newUserMeal = userMealMapper.mapToUserMeal(userSlug, newMealDto);
        userMealRepository.save(newUserMeal);

        return userMealMapper.mapToUserMealDto(userMeal);
    }

    @Override
    public List<UserMealDto> updateUserMeals(String userSlug, List<MealDto> mealDtoList) {
        List<UserMeal> userMealsToUpdate = new ArrayList<>();
        List<UserMealDto> updatedUserMealDtoList = new ArrayList<>();
        try {
            for (MealDto mealDto : mealDtoList) {
                System.out.println("mealDto extracted from the mealDtoList"+mealDto);
                MealDto newMealDto = mealService.updateMeal(mealDto);
                UserMeal userMeal = userMealMapper.mapToUserMeal(userSlug, newMealDto);
                userMealsToUpdate.add(userMeal);
            }
            List<UserMeal> updatedUserMeals = userMealRepository.saveAll(userMealsToUpdate);
            userMealRepository.deleteAll(userMealsToUpdate);
            for (UserMeal savedUserMeal : updatedUserMeals) {
                updatedUserMealDtoList.add(userMealMapper.mapToUserMealDto(savedUserMeal));
            }
            return updatedUserMealDtoList;
        } catch (DataAccessException e) {
            throw new RuntimeException("Error occurred while saving meals: " + e.getMessage(), e);
        } catch (Exception e) {
            throw new RuntimeException("Unexpected error occurred: " + e.getMessage(), e);
        }
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

