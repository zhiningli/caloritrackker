package com.caloriplanner.calorimeter.clos.controllers;

import com.caloriplanner.calorimeter.clos.models.dto.MealDto;
import com.caloriplanner.calorimeter.clos.models.dto.UserMealDto;
import com.caloriplanner.calorimeter.clos.service.UserMealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/{userSlug}/meals")
public class UserMealController {

    @Autowired
    private UserMealService userMealService;

    @PostMapping("/createMeal")
    public ResponseEntity<UserMealDto> createMeal(@PathVariable String userSlug,
                                           @RequestBody MealDto mealDto) {

        UserMealDto savedUserMealDto = userMealService.createUserMeal(userSlug, mealDto);
        return new ResponseEntity<>(savedUserMealDto, HttpStatus.CREATED);
    }

    @PostMapping("/createMealsByBatch")
    public ResponseEntity<List<UserMealDto>> createMealsByBatch(@PathVariable String userSlug,
                                                                @RequestBody List<MealDto> mealDtoList) {

        try {
            List<UserMealDto> savedUserMealDtoList = userMealService.createUserMeals(userSlug, mealDtoList);
            return new ResponseEntity<>(savedUserMealDtoList, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity<List<UserMealDto>> getUserMeals(@PathVariable String userSlug) {
        List<UserMealDto> userMeals = userMealService.getUserMeals(userSlug);
        return ResponseEntity.ok(userMeals);
    }

    @PutMapping("/updateMeal")
    public ResponseEntity<Void> updateMeal(@PathVariable String userSlug,
                                           @RequestBody MealDto mealDto) {

        userMealService.updateUserMeal(userSlug, mealDto);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/updateMealsByBatch")
    public ResponseEntity<List<UserMealDto>> updateMealsByBatch(@PathVariable String userSlug,
                                                                @RequestBody List<MealDto> mealDtoList) {
        try {
            List<UserMealDto> updatedUserMealDtoList = userMealService.updateUserMeals(userSlug, mealDtoList);
            return new ResponseEntity<>(updatedUserMealDtoList, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/deleteMeal")
    public ResponseEntity<Void> deleteUserMeal(@PathVariable String userSlug,
                                               @RequestBody MealDto mealDto) {
        userMealService.deleteUserMeal(userSlug, mealDto);
        return ResponseEntity.ok().build();
    }


    @DeleteMapping("/deleteMealsByBatch")
    public ResponseEntity<Void> deleteMealsByBatch(@PathVariable String userSlug,
                                                   @RequestBody List<MealDto> mealDtoList){
        System.out.println("MealDtoList passed to the backend: "+ mealDtoList);
        try {
            userMealService.deleteUserMeals(userSlug, mealDtoList);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
