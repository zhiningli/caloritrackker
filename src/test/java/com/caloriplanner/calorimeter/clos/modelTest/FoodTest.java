package com.caloriplanner.calorimeter.clos.modelTest;

import com.caloriplanner.calorimeter.clos.exceptions.InvalidInputException;
import com.caloriplanner.calorimeter.clos.models.Food;
import com.caloriplanner.calorimeter.clos.constants.FoodCategory;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class FoodTest {
    @Test
    public void testFoodModel(){
        Food apple = Food.builder()
                .name("Apple")
                .category(FoodCategory.FRUIT)
                .caloriesPerGram(0.52)
                .proteinsPerGram(0.03)
                .fatsPerGram(0.002)
                .carbsPerGram(0.14)
                .build();
        
        apple.setWeight(150);
        
        assertEquals("Apple", apple.getName());
        assertEquals(FoodCategory.FRUIT, apple.getCategory());
        assertEquals(150, apple.getWeight());
        
        assertEquals(0.52 * 150, apple.getTotalCalories(), 0.001);
        assertEquals(0.03 * 150, apple.getTotalProteins(), 0.001);
        assertEquals(0.002 * 150, apple.getTotalFats(), 0.001);
        assertEquals(0.14 * 150, apple.getTotalCarbs(), 0.001);

        // Test invalid weight
        assertThrows(InvalidInputException.class, () ->{
            apple.setWeight(-10);
        });

        // Test invalid nutritional values
        Food invalidFood = Food.builder()
                .name("Invalid Food")
                .category(FoodCategory.OTHER)
                .caloriesPerGram(-0.52)
                .proteinsPerGram(-0.1)
                .fatsPerGram(-0.002)
                .carbsPerGram(-0.14)
                .weight(150)
                .build();

        assertThrows(InvalidInputException.class, invalidFood::validate);
    }
}
