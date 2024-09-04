package com.caloriplanner.calorimeter.clos.service;

import com.caloriplanner.calorimeter.clos.models.Food;
import com.caloriplanner.calorimeter.clos.models.dto.FoodDto;

import java.util.List;

public interface FoodService {
    FoodDto createFood(FoodDto foodDto);
    List<FoodDto> getAllFoods();
    FoodDto getFoodById(String id);
    FoodDto getFoodByName(String name);
    void deleteFood(String name);
}
