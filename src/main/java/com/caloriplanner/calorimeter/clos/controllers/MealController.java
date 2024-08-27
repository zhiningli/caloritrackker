package com.caloriplanner.calorimeter.clos.controllers;

import com.caloriplanner.calorimeter.clos.models.dto.MealDto;
import com.caloriplanner.calorimeter.clos.service.MealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/meals")
public class MealController {

    @Autowired
    private MealService mealService;

    @PostMapping
    public ResponseEntity<MealDto> createMeal(@RequestBody MealDto mealDto) {
        MealDto createdMeal = mealService.createMeal(mealDto);
        return new ResponseEntity<>(createdMeal, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<MealDto>> getAllMeals() {
        List<MealDto> meals = mealService.getAllMeal();
        return new ResponseEntity<>(meals, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MealDto> getMealById(@PathVariable String id) {
        MealDto meal = mealService.getMealById(id);
        return new ResponseEntity<>(meal, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MealDto> updateMeal(@PathVariable String id, @RequestBody MealDto mealDto) {
        MealDto updatedMeal = mealService.updateMeal(id, mealDto);
        return new ResponseEntity<>(updatedMeal, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteMealById(@PathVariable String id) {
        mealService.deleteMealById(id);
        return new ResponseEntity<>("Meal deleted successfully!", HttpStatus.OK);
    }
}
