package com.caloriplanner.calorimeter.clos.service.impl;

import com.caloriplanner.calorimeter.clos.models.Meal;
import com.caloriplanner.calorimeter.clos.models.dto.MealDto;
import com.caloriplanner.calorimeter.clos.repositories.MealRepository;
import com.caloriplanner.calorimeter.clos.service.MealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class MealServiceImpl implements MealService {

    @Autowired
    MealRepository mealRepository;

    @Override
    @Transactional
    public MealDto createMeal(MealDto mealDto) {
        Meal meal;
        return null;
    }

    @Override
    public List<MealDto> getAllMeal() {
        return List.of();
    }

    @Override
    public MealDto getMealById(String id) {
        return null;
    }

    @Override
    public MealDto getMealByName(String id) {
        return null;
    }

    @Override
    public MealDto updateMeal(String id, MealDto mealDto) {
        return null;
    }

    @Override
    public void deleteMealById(String id) {

    }

    @Override
    public void deleteMealByName(String name) {

    }
}
