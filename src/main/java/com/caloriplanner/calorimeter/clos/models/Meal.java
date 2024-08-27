package com.caloriplanner.calorimeter.clos.models;

import com.caloriplanner.calorimeter.clos.constants.FoodCategory;
import com.caloriplanner.calorimeter.clos.exceptions.InvalidInputException;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.util.List;

/**
 * This class represents a meal made up of a list of foods.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "meals")
public class Meal {

    @Id
    private String id;

    private String name;
    private FoodCategory category;

    @DBRef(lazy = false)
    private List<Food> foods;  // Reference to Food objects

    private double caloriesPerGram;
    private double proteinsPerGram;
    private double fatsPerGram;
    private double carbsPerGram;
    private double weight;

    // Constructor for handling MealDto
    public Meal(String name, FoodCategory category, List<Food> foods) {
        this.name = name;
        this.category = category;
        this.foods = foods;
        calculateNutritionalValues();
    }

    public void addFood(Food food) {
        foods.add(food);
        calculateNutritionalValues();
    }

    public void removeFood(Food food) {
        foods.remove(food);
        calculateNutritionalValues();
    }

    public void validate() {
        if (foods == null || foods.isEmpty()) {
            throw new InvalidInputException("Meal must contain at least one food item.");
        }
        foods.forEach(Food::validate);
        if (weight <= 0) {
            throw new InvalidInputException("Total weight must be greater than 0");
        }
    }

    private void calculateNutritionalValues() {
        double totalCalories = 0;
        double totalProteins = 0;
        double totalFats = 0;
        double totalCarbs = 0;
        double totalWeight = 0;

        for (Food food : foods) {
            totalCalories += food.getTotalCalories();
            totalProteins += food.getTotalProteins();
            totalFats += food.getTotalFats();
            totalCarbs += food.getTotalCarbs();
            totalWeight += food.getWeight();
        }

        if (totalWeight > 0) {
            this.caloriesPerGram = totalCalories / totalWeight;
            this.proteinsPerGram = totalProteins / totalWeight;
            this.fatsPerGram = totalFats / totalWeight;
            this.carbsPerGram = totalCarbs / totalWeight;
            this.weight = totalWeight;
        } else {
            throw new InvalidInputException("Total weight must be greater than 0");
        }
    }
}

