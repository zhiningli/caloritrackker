package com.caloriplanner.calorimeter.clos.mapper;

import com.caloriplanner.calorimeter.clos.models.Meal;
import com.caloriplanner.calorimeter.clos.models.dto.MealDto;

public class MealMapper {

    public static MealDto mapToMealDto(Meal meal) {
        return MealDto.builder()
                .name(meal.getName())
                .category(meal.getCategory())
                .caloriesPerGram(meal.getCaloriesPerGram())
                .proteinsPerGram(meal.getProteinsPerGram())
                .fatsPerGram(meal.getFatsPerGram())
                .carbsPerGram(meal.getCarbsPerGram())
                .weight(meal.getWeight())
                .foods(meal.getFoods()) // Map foods
                .build();
    }

    public static Meal mapToMeal(MealDto mealDto) {
        return new Meal(mealDto.getName(),
                mealDto.getCategory(),
                mealDto.getCaloriesPerGram(),
                mealDto.getProteinsPerGram(),
                mealDto.getFatsPerGram(),
                mealDto.getCarbsPerGram(),
                mealDto.getWeight(),
                mealDto.getFoods());
    }
}

