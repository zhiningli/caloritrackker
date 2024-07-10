package com.caloriplanner.calorimeter.clos.models;

import com.caloriplanner.calorimeter.clos.constants.FoodCategory;
import com.caloriplanner.calorimeter.clos.constants.FoodConstants;
import com.caloriplanner.calorimeter.clos.exceptions.InvalidInputException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Food {

    private String name;
    private FoodCategory category;
    private double caloriesPerGram;
    private double proteinsPerGram;
    private double fatsPerGram;
    private double carbsPerGram;
    private double weight; // This field is for user input and can change

    public void setWeight(double weight){
        if (weight <= FoodConstants.MIN_WEIGHT){
            throw new InvalidInputException("Weight must be a positive number. ");
        }
        this.weight = weight;
    }

    //Method to calculate total nutritional values based on weight
    public double getTotalCalories(){
        return caloriesPerGram * weight;
    }

    public double getTotalProteins(){
        return proteinsPerGram * weight;
    }

    public double getTotalFats(){
        return fatsPerGram * weight;
    }

    public double getTotalCarbs(){
        return carbsPerGram * weight;
    }

    public void validate(){
        if (caloriesPerGram < FoodConstants.MIN_NUTRITIONAL_VALUE || proteinsPerGram < FoodConstants.MIN_NUTRITIONAL_VALUE || fatsPerGram < FoodConstants.MIN_NUTRITIONAL_VALUE || carbsPerGram < FoodConstants.MIN_NUTRITIONAL_VALUE ){
            throw new InvalidInputException("Nutritional values must be non-negative.");
        }
        if (weight < FoodConstants.MIN_WEIGHT) {
            throw new InvalidInputException("Weight must be a positive number.");
        }
    }

}
