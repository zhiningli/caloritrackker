package com.caloriplanner.calorimeter.clos.service;

import com.caloriplanner.calorimeter.clos.models.dto.MealDto;

import java.util.List;

public interface MealService {

    MealDto createMeal(MealDto mealDto);
    List<MealDto> getAllMeal();
    MealDto getMealById(String id);
    MealDto getMealByName(String id);
    MealDto updateMeal(String id, MealDto mealDto);
    void deleteMealById(String id);
    void deleteMealByName(String name);
}
