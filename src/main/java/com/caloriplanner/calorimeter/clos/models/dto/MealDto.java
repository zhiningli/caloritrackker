package com.caloriplanner.calorimeter.clos.models.dto;

import com.caloriplanner.calorimeter.clos.constants.MealCategory;
import lombok.*;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MealDto {

    private String id;
    private String name;
    private MealCategory category;
    private double caloriesPerGram;
    private double proteinsPerGram;
    private double fatsPerGram;
    private double carbsPerGram;
    private double weight;

    private String consumptionDate;
    private String consumptionTime;
    private String timeStamp;

    private Map<String, Double> foodNames;
}
