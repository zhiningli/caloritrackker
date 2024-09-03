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

import java.util.HashMap;
import java.util.Map;

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
    private Map<Food, Double> foods = new HashMap<>();

    private double caloriesPerGram;
    private double proteinsPerGram;
    private double fatsPerGram;
    private double carbsPerGram;
    private double weight;

    @Builder
    public Meal(String name, FoodCategory category, Map<Food, Double> foods) {
        this.name = name;
        this.category = category;
        this.foods = foods != null ? foods : new HashMap<>();
        calculateNutritionalValues();
    }

    public void setFoods(Map<Food, Double> foods) {
        if (foods == null || foods.isEmpty()) {
            throw new InvalidInputException("Meal must contain at least one food item.");
        }
        this.foods = foods;
        calculateNutritionalValues();
    }

    private void calculateNutritionalValues() {
        double totalCalories = 0;
        double totalProteins = 0;
        double totalFats = 0;
        double totalCarbs = 0;
        double totalWeight = 0;

        for (Map.Entry<Food, Double> entry : foods.entrySet()) {
            Food food = entry.getKey();
            double weight = entry.getValue();

            totalCalories += food.getCaloriesPerGram() * weight;
            totalProteins += food.getProteinsPerGram() * weight;
            totalFats += food.getFatsPerGram() * weight;
            totalCarbs += food.getCarbsPerGram() * weight;
            totalWeight += weight;
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
