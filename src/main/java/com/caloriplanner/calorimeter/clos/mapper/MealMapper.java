package com.caloriplanner.calorimeter.clos.mapper;

import com.caloriplanner.calorimeter.clos.exceptions.ResourceNotFoundException;
import com.caloriplanner.calorimeter.clos.models.Meal;
import com.caloriplanner.calorimeter.clos.models.dto.MealDto;
import com.caloriplanner.calorimeter.clos.models.Food;
import com.caloriplanner.calorimeter.clos.repositories.FoodRepository;
import com.caloriplanner.calorimeter.clos.repositories.MealRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MealMapper {


    @Autowired
    FoodRepository foodRepository;

    @Autowired
    MealRepository mealRepository;

    public MealDto mapToMealDto(Meal meal) {

        Map<String, Double> foodNames = new HashMap<>();

        if (meal.getFoods() != null){
            for (Map.Entry<Food, Double> entry: meal.getFoods().entrySet()){
                String foodName = entry.getKey().getName();
                Double weight = entry.getValue();
                foodNames.put(foodName, weight);
            }
        }

        return MealDto.builder()
                .name(meal.getName())
                .category(meal.getCategory())
                .caloriesPerGram(meal.getCaloriesPerGram())
                .proteinsPerGram(meal.getProteinsPerGram())
                .fatsPerGram(meal.getFatsPerGram())
                .carbsPerGram(meal.getCarbsPerGram())
                .foodNames(foodNames)
                .build();
    }

    public Meal mapToMeal(MealDto mealDto) {

        Map<Food, Double> foods = new HashMap<>();
        Double totalWeight = 0.0;

        if (mealDto.getFoodNames() != null){
            for (Map.Entry<String, Double> entry : mealDto.getFoodNames().entrySet()) {
                String foodName = entry.getKey();
                Double weight = entry.getValue();
                Food food = foodRepository.findByName(foodName);
                if (food != null) {
                    foods.put(food, weight);
                    totalWeight += weight;
                } else {
                    throw new ResourceNotFoundException("Food not found with name: " + foodName);
                }

            }
        }

        return Meal.builder()
                .name(mealDto.getName())
                .category(mealDto.getCategory())
                .caloriesPerGram(mealDto.getCaloriesPerGram())
                .proteinsPerGram(mealDto.getProteinsPerGram())
                .carbsPerGram(mealDto.getCarbsPerGram())
                .fatsPerGram(mealDto.getFatsPerGram())
                .foods(foods)
                .weight(totalWeight)
                .build();
    }
}

