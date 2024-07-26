package com.caloriplanner.calorimeter.clos.models;

import com.caloriplanner.calorimeter.clos.constants.FoodCategory;
import com.caloriplanner.calorimeter.clos.exceptions.InvalidInputException;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;



/**
 * This class uses the composite pattern to create a meal object made up of a list of foods.
 */

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@Document(collection = "meals")
public class Meal extends Food {

    @Singular
    private List<Food> foods = new ArrayList<>();

    // This constructor is just to handle the mealDto
    public Meal(String name, FoodCategory category, double caloriesPerGram,
                double proteinsPerGram, double fatsPerGram, double carbsPerGram, double weight,
                List<Food> foods) {
        this.setName(name);
        this.setCategory(category);
        this.setCaloriesPerGram(caloriesPerGram);
        this.setProteinsPerGram(proteinsPerGram);
        this.setFatsPerGram(fatsPerGram);
        this.setCarbsPerGram(carbsPerGram);
        this.setWeight(weight);
        this.foods = foods != null ? foods : new ArrayList<>();
    }

    // Constructor for an input of ArrayList
    public Meal(List<Food> foods) {
        this.foods = foods != null ? foods : new ArrayList<>();
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

    @Override
    public void validate() {
        super.validate();
        if (foods == null || foods.isEmpty()) {
            throw new InvalidInputException("Meal must contain at least one food item.");
        }
        foods.forEach(Food::validate);
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
            this.setCaloriesPerGram(totalCalories / totalWeight);
            this.setProteinsPerGram(totalProteins / totalWeight);
            this.setFatsPerGram(totalFats / totalWeight);
            this.setCarbsPerGram(totalCarbs / totalWeight);
            this.setWeight(totalWeight);
        } else {
            throw new InvalidInputException("Total weight must be greater than 0");
        }
    }
}
