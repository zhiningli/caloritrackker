package com.caloriplanner.calorimeter.clos.controllers;

import com.caloriplanner.calorimeter.clos.models.User;
import com.caloriplanner.calorimeter.clos.models.UserMeal;
import com.caloriplanner.calorimeter.clos.models.dto.MealDto;
import com.caloriplanner.calorimeter.clos.service.UserMealService;
import com.caloriplanner.calorimeter.clos.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/{userSlug}/meals")
public class UserMealController {

    @Autowired
    private UserMealService userMealService;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/createMeal")
    public ResponseEntity<Void> createMeal(@PathVariable String userSlug,
                                           @RequestBody MealDto mealDto,
                                           @RequestParam double weight) {
        User user = userRepository.findBySlug(userSlug);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        userMealService.createUserMeal(user.getId(), mealDto, weight);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<UserMeal>> getUserMeals(@PathVariable String userSlug) {
        User user = userRepository.findBySlug(userSlug);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        List<UserMeal> userMeals = userMealService.getUserMeals(user.getId());
        return ResponseEntity.ok(userMeals);
    }

    @PutMapping("/updateMeal/{mealId}")
    public ResponseEntity<Void> updateMeal(@PathVariable String userSlug,
                                           @PathVariable String mealId,
                                           @RequestBody MealDto mealDto,
                                           @RequestParam double weight) {

        User user = userRepository.findBySlug(userSlug);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        userMealService.deleteUserMeal(user.getId(), mealId);
        userMealService.createUserMeal(user.getId(), mealDto, weight);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/deleteMeal/{mealId}")
    public ResponseEntity<Void> deleteUserMeal(@PathVariable String userSlug,
                                               @PathVariable String mealId) {
        // Fetch the user by slug
        User user = userRepository.findBySlug(userSlug);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        // Use userId in the service
        userMealService.deleteUserMeal(user.getId(), mealId);
        return ResponseEntity.ok().build();
    }
}
