package com.caloriplanner.calorimeter.clos.mapper;

import com.caloriplanner.calorimeter.clos.exceptions.ResourceNotFoundException;
import com.caloriplanner.calorimeter.clos.models.Meal;
import com.caloriplanner.calorimeter.clos.models.dto.MealDto;
import com.caloriplanner.calorimeter.clos.models.Food;
import com.caloriplanner.calorimeter.clos.repositories.FoodRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MealMapper {

    private static FoodRepository foodRepository = null;

    // Constructor injection
    public MealMapper(FoodRepository foodRepository) {
        MealMapper.foodRepository = foodRepository;
    }

    public static MealDto mapToMealDto(Meal meal) {
        return MealDto.builder()
                .name(meal.getName())
                .category(meal.getCategory())
                .caloriesPerGram(meal.getCaloriesPerGram())
                .proteinsPerGram(meal.getProteinsPerGram())
                .fatsPerGram(meal.getFatsPerGram())
                .carbsPerGram(meal.getCarbsPerGram())
                .weight(meal.getWeight())
                .foods(meal.getFoods().stream()
                        .map(Food::getId)
                        .collect(Collectors.toList()))
                .build();
    }

    public static Meal mapToMeal(MealDto mealDto) {
        // Create an empty list to hold the Food objects
        List<Food> foods = new ArrayList<>();

        for (String foodId : mealDto.getFoods()) {
            Food food = foodRepository.findById(foodId)
                    .orElseThrow(() -> new ResourceNotFoundException("Food with id " + foodId + " not found"));

            foods.add(food);
        }

        return new Meal(mealDto.getName(),
                mealDto.getCategory(),
                foods);
    }
}


