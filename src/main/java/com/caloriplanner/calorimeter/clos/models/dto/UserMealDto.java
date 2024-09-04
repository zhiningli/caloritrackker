package com.caloriplanner.calorimeter.clos.models.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserMealDto {

    private String id;
    private MealDto mealDto;
    private String userSlug;
}
