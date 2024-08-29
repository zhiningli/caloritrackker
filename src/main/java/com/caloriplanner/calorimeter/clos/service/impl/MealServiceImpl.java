package com.caloriplanner.calorimeter.clos.service.impl;

import com.caloriplanner.calorimeter.clos.exceptions.ResourceNotFoundException;
import com.caloriplanner.calorimeter.clos.mapper.MealMapper;
import com.caloriplanner.calorimeter.clos.models.Food;
import com.caloriplanner.calorimeter.clos.models.Meal;
import com.caloriplanner.calorimeter.clos.models.dto.MealDto;
import com.caloriplanner.calorimeter.clos.repositories.FoodRepository;
import com.caloriplanner.calorimeter.clos.repositories.MealRepository;
import com.caloriplanner.calorimeter.clos.service.MealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.ArrayList;

@Service
public class MealServiceImpl implements MealService {

    @Autowired
    MealRepository mealRepository;

    @Autowired
    FoodRepository foodRepository;

    @Autowired
    MealMapper mealMapper;

    @Override
    @Transactional
    public MealDto createMeal(MealDto mealDto) {
        List<Food> foods = new ArrayList<>();

        // Fetch the Food entities using the provided food names
        for (String foodName : mealDto.getFoodNames()) {
            Food food = foodRepository.findByName(foodName);

            if (food != null) {
                foods.add(food);
            } else {
                throw new ResourceNotFoundException("Food not found with name: " + foodName);
            }
        }

        Meal meal = Meal.builder()
                .name(mealDto.getName())
                .category(mealDto.getCategory())
                .foods(foods)
                .build();
        System.out.println(foods);
        mealRepository.save(meal);

        return mealMapper.mapToMealDto(meal);
    }

    @Override
    @Transactional
    public List<MealDto> getAllMeal() {
        List<Meal> meals = mealRepository.findAll();
        return meals.stream().map(mealMapper::mapToMealDto).toList();
    }

    @Override
    public MealDto getMealById(String id){
        Meal meal = mealRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("No meal with id "+id+" found!"));
        return mealMapper.mapToMealDto(meal);
    }

    @Override
    public MealDto getMealByName(String name) {
        Meal meal = mealRepository.findByName(name);
        if (meal != null){
            return mealMapper.mapToMealDto(meal);
        } else {
            throw new ResourceNotFoundException("No meal with name " + name + " found!");
        }
    }

    @Override
    public MealDto updateMeal(String id, MealDto mealDto) {
        Meal meal= mealRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("No meal with id " + id + "found! Unable to update meal.")
        );

        meal.setName(mealDto.getName());
        meal.setCategory(mealDto.getCategory());
        meal.setCaloriesPerGram(mealDto.getCaloriesPerGram());
        meal.setProteinsPerGram(mealDto.getProteinsPerGram());
        meal.setFatsPerGram(mealDto.getFatsPerGram());
        meal.setCarbsPerGram(mealDto.getCarbsPerGram());
        Meal updatedMeal = mealRepository.save(meal);
        return mealMapper.mapToMealDto(updatedMeal);
    }

    @Override
    public void deleteMealById(String id) {
        Meal meal = mealRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Unable to find meal with id " + id + ", unable to perform deletion"));
        mealRepository.delete(meal);
    }

    @Override
    public void deleteMealByName(String name) {
        Meal meal = mealRepository.findByName(name);
        if (meal != null){
            mealRepository.delete(meal);
        } else {
            throw new ResourceNotFoundException("Unable to find meal with name " + name +", unable to perform deletion");
        }
    }
}
