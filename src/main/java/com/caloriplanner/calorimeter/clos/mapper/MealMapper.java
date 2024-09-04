package com.caloriplanner.calorimeter.clos.mapper;

import com.caloriplanner.calorimeter.clos.exceptions.InvalidInputException;
import com.caloriplanner.calorimeter.clos.models.Meal;
import com.caloriplanner.calorimeter.clos.models.dto.FoodDto;
import com.caloriplanner.calorimeter.clos.models.dto.MealDto;
import com.caloriplanner.calorimeter.clos.models.Food;
import com.caloriplanner.calorimeter.clos.service.impl.FoodServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Component
public class MealMapper {

    @Autowired
    FoodServiceImpl foodService;

    @Autowired
    FoodMapper foodMapper;

    public MealDto mapToMealDto(Meal meal) {
        Map<String, Double> foodNames = new HashMap<>();

        if (meal.getFoods() != null) {
            for (Map.Entry<String, Double> entry : meal.getFoods().entrySet()) {
                String foodID = entry.getKey();
                Double weight = entry.getValue();
                FoodDto food = foodService.getFoodById(foodID);
                String foodName = food.getName();
                foodNames.put(foodName, weight);
            }
        }

        return MealDto.builder()
                .id(meal.getId())
                .name(meal.getName())
                .category(meal.getCategory())
                .foodNames(foodNames)
                .build();
    }

    public Meal mapToMeal(MealDto mealDto) {
        Map<String, Double> foods = new HashMap<>();
        Map<String, Food> foodMap = new HashMap<>();
        Double totalWeight = 0.0;

        if (mealDto.getFoodNames() != null) {
            for (Map.Entry<String, Double> entry : mealDto.getFoodNames().entrySet()) {
                String foodName = entry.getKey();
                Double weight = entry.getValue();

                FoodDto foodDto = foodService.getFoodByName(foodName);
                Food food = foodMapper.mapToFood(foodDto);

                String foodID = food.getId();
                foods.put(foodID, weight);
                foodMap.put(foodID, food);
                totalWeight += weight;
            }
        }

        Meal meal = Meal.builder()
                .id(mealDto.getId() != null ? mealDto.getId() : UUID.randomUUID().toString())
                .name(mealDto.getName())
                .category(mealDto.getCategory())
                .foods(foods)
                .weight(totalWeight)
                .build();

        calculateNutritionalValues(meal, foodMap);

        return meal;
    }

    private void calculateNutritionalValues(Meal meal, Map<String, Food> foodMap) {
        double totalCalories = 0;
        double totalProteins = 0;
        double totalFats = 0;
        double totalCarbs = 0;
        double totalWeight = 0;

        for (Map.Entry<String, Double> entry : meal.getFoods().entrySet()) {
            String foodID = entry.getKey();
            double weight = entry.getValue();

            Food food = foodMap.get(foodID);
            totalCalories += food.getCaloriesPerGram() * weight;
            totalProteins += food.getProteinsPerGram() * weight;
            totalFats += food.getFatsPerGram() * weight;
            totalCarbs += food.getCarbsPerGram() * weight;
            totalWeight += weight;
        }

        if (totalWeight > 0) {
            meal.setCaloriesPerGram(totalCalories / totalWeight);
            meal.setProteinsPerGram(totalProteins / totalWeight);
            meal.setFatsPerGram(totalFats / totalWeight);
            meal.setCarbsPerGram(totalCarbs / totalWeight);
            meal.setWeight(totalWeight);
        } else {
            throw new InvalidInputException("Total weight must be greater than 0");
        }
    }
}
