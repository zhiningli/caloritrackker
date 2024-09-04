package com.caloriplanner.calorimeter.clos.mapper;

import com.caloriplanner.calorimeter.clos.models.Food;
import com.caloriplanner.calorimeter.clos.models.dto.FoodDto;

import java.util.UUID;

public class FoodMapper {

    public static FoodDto mapToFoodDto(Food food) {
        return FoodDto.builder()
                .id(food.getId())  // Map the UUID from the entity to the DTO
                .name(food.getName())
                .category(food.getCategory())
                .caloriesPerGram(food.getCaloriesPerGram())
                .proteinsPerGram(food.getProteinsPerGram())
                .fatsPerGram(food.getFatsPerGram())
                .carbsPerGram(food.getCarbsPerGram())
                .build();
    }

    public static Food mapToFood(FoodDto foodDto) {
        return Food.builder()
                .id(foodDto.getId() != null ? foodDto.getId() : UUID.randomUUID().toString())
                .name(foodDto.getName())
                .category(foodDto.getCategory())
                .caloriesPerGram(foodDto.getCaloriesPerGram())
                .proteinsPerGram(foodDto.getProteinsPerGram())
                .fatsPerGram(foodDto.getFatsPerGram())
                .carbsPerGram(foodDto.getCarbsPerGram())
                .build();
    }
}

