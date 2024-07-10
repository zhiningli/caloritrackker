package com.caloriplanner.calorimeter.clos.service;

import com.caloriplanner.calorimeter.clos.models.Food;

import java.util.List;

public interface FoodService {
    List<Food> getAllFoods();
    Food createFood(Food food);
    Food getFoodByName(String name);
    Food saveFood(Food food);
    void deleteFood(String name);
}
