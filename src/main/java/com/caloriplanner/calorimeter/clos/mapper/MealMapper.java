package com.caloriplanner.calorimeter.clos.mapper;

import com.caloriplanner.calorimeter.clos.exceptions.ResourceNotFoundException;
import com.caloriplanner.calorimeter.clos.models.Meal;
import com.caloriplanner.calorimeter.clos.models.dto.MealDto;
import com.caloriplanner.calorimeter.clos.models.Food;
import com.caloriplanner.calorimeter.clos.repositories.FoodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class MealMapper {

    private final FoodRepository foodRepository;

    @Autowired
    public MealMapper(FoodRepository foodRepository) {
        this.foodRepository = foodRepository;
    }

    public MealDto mapToMealDto(Meal meal) {
        return MealDto.builder()
                .name(meal.getName())
                .category(meal.getCategory())
                .caloriesPerGram(meal.getCaloriesPerGram())
                .proteinsPerGram(meal.getProteinsPerGram())
                .fatsPerGram(meal.getFatsPerGram())
                .carbsPerGram(meal.getCarbsPerGram())
                .weight(meal.getWeight())
                .foodNames(meal.getFoods().stream()
                        .map(Food::getName)
                        .collect(Collectors.toList()))
                .build();
    }

    public Meal mapToMeal(MealDto mealDto) {
        if (mealDto.getFoodNames() == null || mealDto.getFoodNames().isEmpty()) {
            throw new IllegalArgumentException("Meal must contain at least one food name.");
        }

        List<Food> foods = mealDto.getFoodNames().stream()
                .map(foodName -> {
                    Food food = foodRepository.findByName(foodName);
                    if (food == null) {
                        throw new ResourceNotFoundException("Food not found with name: " + foodName);
                    }
                    return food;
                })
                .collect(Collectors.toList());

        return new Meal(mealDto.getName(),
                mealDto.getCategory(),
                foods);
    }
}

