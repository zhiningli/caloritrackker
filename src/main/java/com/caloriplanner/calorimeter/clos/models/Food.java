package com.caloriplanner.calorimeter.clos.models;

import com.caloriplanner.calorimeter.clos.constants.FoodCategory;
import com.caloriplanner.calorimeter.clos.constants.FoodConstants;
import com.caloriplanner.calorimeter.clos.exceptions.InvalidInputException;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "foods")
public class Food {

    @Id
    private String id;
    private String name;
    private FoodCategory category;
    private double caloriesPerGram;
    private double proteinsPerGram;
    private double fatsPerGram;
    private double carbsPerGram;

    public void validate() {
        if (caloriesPerGram < FoodConstants.MIN_NUTRITIONAL_VALUE || proteinsPerGram < FoodConstants.MIN_NUTRITIONAL_VALUE || fatsPerGram < FoodConstants.MIN_NUTRITIONAL_VALUE || carbsPerGram < FoodConstants.MIN_NUTRITIONAL_VALUE) {
            throw new InvalidInputException("Nutritional values must be non-negative.");
        }
    }

}
