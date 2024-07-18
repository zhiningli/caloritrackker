package com.caloriplanner.calorimeter.clos.mapper;


import com.caloriplanner.calorimeter.clos.models.Food;
import com.caloriplanner.calorimeter.clos.models.dto.FoodDto;

public class FoodMapper {

    public static FoodDto mapToFoodDto (Food food){
        return FoodDto.builder()
                .name(food.getName())
                .category(food.getCategory())
                .caloriesPerGram(food.getCaloriesPerGram())
                .proteinsPerGram(food.getProteinsPerGram())
                .fatsPerGram(food.getFatsPerGram())
                .carbsPerGram(food.getCarbsPerGram())
                .weight(food.getWeight())
                .build();
    }

    public static Food mapToFood(FoodDto foodDto){
        return Food.builder().
                name(foodDto.getName())
                .category(foodDto.getCategory())
                .caloriesPerGram(foodDto.getCaloriesPerGram())
                .proteinsPerGram(foodDto.getProteinsPerGram())
                .fatsPerGram(foodDto.getFatsPerGram())
                .carbsPerGram(foodDto.getCarbsPerGram())
                .weight(foodDto.getWeight())
                .build();
    }
}
