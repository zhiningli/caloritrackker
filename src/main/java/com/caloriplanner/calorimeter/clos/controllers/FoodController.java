package com.caloriplanner.calorimeter.clos.controllers;

import com.caloriplanner.calorimeter.clos.models.dto.FoodDto;
import com.caloriplanner.calorimeter.clos.service.FoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/foods")
public class FoodController {

    @Autowired
    private FoodService foodService;

    @PostMapping("/create")
    public ResponseEntity<FoodDto> createFood(@RequestBody FoodDto foodDto) {
        FoodDto savedFoodDto = foodService.createFood(foodDto);
        return new ResponseEntity<>(savedFoodDto, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<FoodDto>> getAllFood() {
        List<FoodDto> foodsDto = foodService.getAllFoods();
        return ResponseEntity.ok(foodsDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FoodDto> getFoodById(@PathVariable("id") String id) {
        FoodDto foodDto = foodService.getFoodById(id);
        return ResponseEntity.ok(foodDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteFood(@PathVariable("id") String id) {
        foodService.deleteFood(id);
        return ResponseEntity.ok("Food deleted successfully!");
    }
}
