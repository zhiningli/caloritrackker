package com.caloriplanner.calorimeter.clos.controllers;


import com.caloriplanner.calorimeter.clos.models.Food;
import com.caloriplanner.calorimeter.clos.service.FoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/foods")
public class FoodController {

    @Autowired
    private FoodService foodService;

    @GetMapping
    public List<Food> getAllFoods() {
        return foodService.getAllFoods();
    }

    @GetMapping("/{name}")
    public Food getFoodByName(@PathVariable String name) {
        return foodService.getFoodByName(name);
    }

    @PostMapping
    public Food saveFood(@RequestBody Food food) {
        return foodService.saveFood(food);
    }

    @DeleteMapping("/{name}")
    public void deleteFood(@PathVariable String name) {
        foodService.deleteFood(name);
    }

    @PostMapping("/create")
    public Food createFood(@RequestBody Food food) {
        return foodService.createFood(food);
    }
}