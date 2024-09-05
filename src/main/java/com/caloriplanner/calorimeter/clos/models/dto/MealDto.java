package com.caloriplanner.calorimeter.clos.models.dto;

import com.caloriplanner.calorimeter.clos.constants.FoodCategory;
import com.caloriplanner.calorimeter.clos.models.Food;
import lombok.*;

import java.util.HashMap;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MealDto {

    private String id;
    private String name;
    private FoodCategory category;
    private double caloriesPerGram;
    private double proteinsPerGram;
    private double fatsPerGram;
    private double carbsPerGram;
    private double weight;
    private Map<String, Double> foodNames;
}
