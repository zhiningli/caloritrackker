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
    public ResponseEntity<MealDto> createMeal(MealDto mealDto){
        MealDto createdMeal = mealService.createMeal(mealDto);
        return new ResponseEntity<>(createdMeal, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<MealDto>> getAllMeals(){
        List<MealDto> meals = mealService.getAllMeal();
        return ResponseEntity.ok(meals);
    }

    @GetMapping("{id}")
    public ResponseEntity<MealDto> getMealById(@PathVariable("id") String id){
        MealDto meal = mealService.getMealById(id);
        return ResponseEntity.ok(meal);
    }

    @PutMapping("{id}")
    public ResponseEntity<MealDto> updateMeal(@PathVariable("id") String id, @RequestBody MealDto mealDto){
        MealDto updatedMeal = mealService.updateMeal(id, mealDto);
        return ResponseEntity.ok(updatedMeal);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteById(@PathVariable("id") String id){
        mealService.deleteMealById(id);
        return ResponseEntity.ok("Meal deleted successfully!");
    }
}
