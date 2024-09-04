package com.caloriplanner.calorimeter.servicesTest;

import com.caloriplanner.calorimeter.clos.constants.FoodCategory;
import com.caloriplanner.calorimeter.clos.exceptions.ResourceNotFoundException;
import com.caloriplanner.calorimeter.clos.mapper.FoodMapper;
import com.caloriplanner.calorimeter.clos.models.Food;
import com.caloriplanner.calorimeter.clos.models.dto.FoodDto;
import com.caloriplanner.calorimeter.clos.repositories.FoodRepository;
import com.caloriplanner.calorimeter.clos.service.impl.FoodServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class FoodServiceImplTest {

    @Mock
    private FoodRepository foodRepository;

    @Mock
    private FoodMapper foodMapper;

    @InjectMocks
    private FoodServiceImpl foodService;

    private Food food;
    private FoodDto foodDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        food = new Food();
        food.setName("Apple");
        food.setCategory(FoodCategory.FRUIT);
        food.setCaloriesPerGram(0.52);
        food.setProteinsPerGram(0.03);
        food.setFatsPerGram(0.002);
        food.setCarbsPerGram(0.14);

        foodDto = foodMapper.mapToFoodDto(food);
    }

    @Test
    void createFood() {
        when(foodRepository.save(any(Food.class))).thenReturn(food);

        FoodDto savedFoodDto = foodService.createFood(foodDto);

        assertNotNull(savedFoodDto);
        assertEquals(food.getName(), savedFoodDto.getName());
        verify(foodRepository, times(1)).save(any(Food.class));
    }

    @Test
    void getAllFoods() {
        when(foodRepository.findAll()).thenReturn(Arrays.asList(food));

        List<FoodDto> foodDtos = foodService.getAllFoods();

        assertEquals(1, foodDtos.size());
        verify(foodRepository, times(1)).findAll();
    }

    @Test
    void getFoodByName() {
        when(foodRepository.findByName(anyString())).thenReturn(food);

        FoodDto foundFoodDto = foodService.getFoodByName("Apple");

        assertNotNull(foundFoodDto);
        assertEquals(food.getName(), foundFoodDto.getName());
        verify(foodRepository, times(1)).findByName(anyString());
    }

    @Test
    void getFoodByName_NotFound() {
        when(foodRepository.findByName(anyString())).thenReturn(null);

        assertThrows(ResourceNotFoundException.class, () -> {
            foodService.getFoodByName("NonExistingFood");
        });
    }

    @Test
    void deleteFood() {
        when(foodRepository.findByName(anyString())).thenReturn(food);
        doNothing().when(foodRepository).deleteByName(anyString());

        foodService.deleteFood("Apple");

        verify(foodRepository, times(1)).findByName(anyString());
        verify(foodRepository, times(1)).deleteByName(anyString());
    }

    @Test
    void deleteFood_NotFound() {
        when(foodRepository.findByName(anyString())).thenReturn(null);

        assertThrows(ResourceNotFoundException.class, () -> {
            foodService.deleteFood("NonExistingFood");
        });
    }
}

