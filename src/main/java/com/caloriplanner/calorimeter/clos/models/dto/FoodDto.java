package com.caloriplanner.calorimeter.clos.models.dto;

import com.caloriplanner.calorimeter.clos.constants.FoodCategory;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FoodDto {

    private String id;
    private String name;
    private FoodCategory category;
    private double caloriesPerGram;
    private double proteinsPerGram;
    private double fatsPerGram;
    private double carbsPerGram;
}
