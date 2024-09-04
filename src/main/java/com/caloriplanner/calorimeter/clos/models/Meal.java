package com.caloriplanner.calorimeter.clos.models;

import com.caloriplanner.calorimeter.clos.constants.FoodCategory;
import com.caloriplanner.calorimeter.clos.exceptions.InvalidInputException;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "meals")
public class Meal {

    @Id
    @Builder.Default
    private String id = UUID.randomUUID().toString();

    private String name;
    private FoodCategory category;

    @Builder.Default
    private Map<String, Double> foods = new HashMap<>();

    private double caloriesPerGram;
    private double proteinsPerGram;
    private double fatsPerGram;
    private double carbsPerGram;
    private double weight;

    @Builder
    public Meal(String name, FoodCategory category, Map<String, Double> foods, Double weight) {
        this.name = name;
        this.category = category;
        this.foods = foods != null ? foods : new HashMap<>();
        this.weight = weight;
    }

    public void setFoods(Map<String, Double> foods) {
        if (foods == null || foods.isEmpty()) {
            throw new InvalidInputException("Meal must contain at least one food item.");
        }
        this.foods = foods;
    }
}
