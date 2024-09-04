package com.caloriplanner.calorimeter.clos.controllers;

import com.caloriplanner.calorimeter.clos.models.User;
import com.caloriplanner.calorimeter.clos.models.UserMeal;
import com.caloriplanner.calorimeter.clos.models.dto.MealDto;
import com.caloriplanner.calorimeter.clos.models.dto.UserMealDto;
import com.caloriplanner.calorimeter.clos.service.UserMealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/{userSlug}/meals")
public class UserMealController {

    @Autowired
    private UserMealService userMealService;


    @PostMapping("/createMeal")
    public ResponseEntity<Void> createMeal(@PathVariable String userSlug,
                                           @RequestBody MealDto mealDto) {

        userMealService.createUserMeal(userSlug, mealDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<UserMealDto>> getUserMeals(@PathVariable String userSlug) {
        List<UserMealDto> userMeals = userMealService.getUserMeals(userSlug);
        return ResponseEntity.ok(userMeals);
    }

    @PutMapping("/updateMeal")
    public ResponseEntity<Void> updateMeal(@PathVariable String userSlug,
                                           @RequestBody MealDto mealDto) {

        userMealService.deleteUserMeal(userSlug, mealDto);
        userMealService.createUserMeal(userSlug, mealDto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/deleteMeal")
    public ResponseEntity<Void> deleteUserMeal(@PathVariable String userSlug,
                                               @RequestBody MealDto mealDto) {

        userMealService.deleteUserMeal(userSlug, mealDto);
        return ResponseEntity.ok().build();
    }
}
