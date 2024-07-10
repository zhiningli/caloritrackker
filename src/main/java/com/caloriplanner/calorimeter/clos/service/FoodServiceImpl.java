package com.caloriplanner.calorimeter.clos.service;

import com.caloriplanner.calorimeter.clos.models.Food;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class FoodServiceImpl implements FoodService {

    private final List<Food> foodList = new ArrayList<>();

    @Override
    public List<Food> getAllFoods() {
        System.out.println("getAllFoods called, returning: " + foodList);
        return foodList;
    }

    @Override
    public Food getFoodByName(String name) {
        Optional<Food> food = foodList.stream()
                .filter(f -> f.getName().equalsIgnoreCase(name))
                .findFirst();
        return food.orElse(null);
    }

    @Override
    public Food saveFood(Food food) {
        foodList.add(food);
        System.out.println("Food added: " + food);
        return food;
    }

    @Override
    public void deleteFood(String name) {
        foodList.removeIf(f -> f.getName().equalsIgnoreCase(name));
    }

    @Override
    public Food createFood(Food food) {
        if (getFoodByName(food.getName()) != null) {
            throw new IllegalArgumentException("Food with this name already exists");
        }
        foodList.add(food);
        return food;
    }
}

