package com.caloriplanner.calorimeter.servicesTest;

import com.caloriplanner.calorimeter.clos.exceptions.ResourceNotFoundException;
import com.caloriplanner.calorimeter.clos.mapper.MealMapper;
import com.caloriplanner.calorimeter.clos.models.Meal;
import com.caloriplanner.calorimeter.clos.models.dto.MealDto;
import com.caloriplanner.calorimeter.clos.models.Food;
import com.caloriplanner.calorimeter.clos.repositories.MealRepository;
import com.caloriplanner.calorimeter.clos.service.impl.MealServiceImpl;
import com.caloriplanner.calorimeter.clos.constants.FoodCategory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class MealServiceImplTest {

    @Mock
    private MealRepository mealRepository;

    @Mock
    private MealMapper mealMapper;

    @InjectMocks
    private MealServiceImpl mealService;

    private Meal meal;
    private MealDto mealDto;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);

        // Initialize Meal and MealDto
        Map<String, Double> foodNames = new HashMap<>();
        foodNames.put("Apple", 100.0);
        foodNames.put("Banana", 50.0);

        Food apple = new Food();
        apple.setId("1");
        apple.setName("Apple");
        apple.setCategory(FoodCategory.FRUIT);
        apple.setCaloriesPerGram(0.52);
        apple.setProteinsPerGram(0.03);
        apple.setFatsPerGram(0.002);
        apple.setCarbsPerGram(0.14);

        Food banana = new Food();
        banana.setId("2");
        banana.setName("Banana");
        banana.setCategory(FoodCategory.FRUIT);
        banana.setCaloriesPerGram(0.89);
        banana.setProteinsPerGram(0.011);
        banana.setFatsPerGram(0.003);
        banana.setCarbsPerGram(0.23);

        mealDto = MealDto.builder()
                .name("Fruit Salad")
                .category(FoodCategory.FRUIT)
                .foodNames(foodNames)
                .build();

        Map<Food, Double> foods = new HashMap<>();
        foods.put(apple, 100.0);
        foods.put(banana, 50.0);

        meal = Meal.builder()
                .name("Fruit Salad")
                .category(FoodCategory.FRUIT)
                .foods(foods)
                .caloriesPerGram(0.6)
                .proteinsPerGram(0.02)
                .fatsPerGram(0.01)
                .carbsPerGram(0.14)
                .build();
    }

    @Test
    void createMealTest() {
        when(mealMapper.mapToMeal(any(MealDto.class))).thenReturn(meal);
        when(mealRepository.save(any(Meal.class))).thenReturn(meal);
        when(mealMapper.mapToMealDto(any(Meal.class))).thenReturn(mealDto);

        MealDto createdMealDto = mealService.createMeal(mealDto);

        assertNotNull(createdMealDto);
        assertEquals(mealDto.getName(), createdMealDto.getName());
        verify(mealRepository, times(1)).save(any(Meal.class));
        verify(mealMapper, times(1)).mapToMeal(any(MealDto.class));
        verify(mealMapper, times(1)).mapToMealDto(any(Meal.class));
    }

    @Test
    void getAllMealTest() {
        when(mealRepository.findAll()).thenReturn(Collections.singletonList(meal));
        when(mealMapper.mapToMealDto(any(Meal.class))).thenReturn(mealDto);

        List<MealDto> mealDtos = mealService.getAllMeal();

        assertEquals(1, mealDtos.size());
        assertEquals(mealDto.getName(), mealDtos.get(0).getName());
        verify(mealRepository, times(1)).findAll();
        verify(mealMapper, times(1)).mapToMealDto(any(Meal.class));
    }

    @Test
    void getMealByIdTest() {
        when(mealRepository.findById(anyString())).thenReturn(Optional.of(meal));
        when(mealMapper.mapToMealDto(any(Meal.class))).thenReturn(mealDto);

        MealDto foundMealDto = mealService.getMealById("1");

        assertNotNull(foundMealDto);
        assertEquals(mealDto.getName(), foundMealDto.getName());
        verify(mealRepository, times(1)).findById(anyString());
        verify(mealMapper, times(1)).mapToMealDto(any(Meal.class));
    }

    @Test
    void getMealByIdNotFoundTest() {
        when(mealRepository.findById(anyString())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> mealService.getMealById("1"));
        verify(mealRepository, times(1)).findById(anyString());
        verify(mealMapper, never()).mapToMealDto(any(Meal.class));
    }

    @Test
    void getMealByNameTest() {
        when(mealRepository.findByName(anyString())).thenReturn(meal);
        when(mealMapper.mapToMealDto(any(Meal.class))).thenReturn(mealDto);

        MealDto foundMealDto = mealService.getMealByName("Fruit Salad");

        assertNotNull(foundMealDto);
        assertEquals(mealDto.getName(), foundMealDto.getName());
        verify(mealRepository, times(1)).findByName(anyString());
        verify(mealMapper, times(1)).mapToMealDto(any(Meal.class));
    }

    @Test
    void getMealByNameNotFoundTest() {
        when(mealRepository.findByName(anyString())).thenReturn(null);

        assertThrows(ResourceNotFoundException.class, () -> mealService.getMealByName("Fruit Salad"));
        verify(mealRepository, times(1)).findByName(anyString());
        verify(mealMapper, never()).mapToMealDto(any(Meal.class));
    }

    @Test
    void updateMealTest() {
        when(mealRepository.findById(anyString())).thenReturn(Optional.of(meal));
        when(mealRepository.save(any(Meal.class))).thenReturn(meal);
        when(mealMapper.mapToMealDto(any(Meal.class))).thenReturn(mealDto);

        MealDto updatedMealDto = mealService.updateMeal("1", mealDto);

        assertNotNull(updatedMealDto);
        assertEquals(mealDto.getName(), updatedMealDto.getName());
        verify(mealRepository, times(1)).findById(anyString());
        verify(mealRepository, times(1)).save(any(Meal.class));
        verify(mealMapper, times(1)).mapToMealDto(any(Meal.class));
    }

    @Test
    void updateMealNotFoundTest() {
        when(mealRepository.findById(anyString())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> mealService.updateMeal("1", mealDto));
        verify(mealRepository, times(1)).findById(anyString());
        verify(mealRepository, never()).save(any(Meal.class));
        verify(mealMapper, never()).mapToMealDto(any(Meal.class));
    }

    @Test
    void deleteMealByIdTest() {
        when(mealRepository.findById(anyString())).thenReturn(Optional.of(meal));

        mealService.deleteMealById("1");

        verify(mealRepository, times(1)).findById(anyString());
        verify(mealRepository, times(1)).delete(any(Meal.class));
    }

    @Test
    void deleteMealByIdNotFoundTest() {
        when(mealRepository.findById(anyString())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> mealService.deleteMealById("1"));
        verify(mealRepository, times(1)).findById(anyString());
        verify(mealRepository, never()).delete(any(Meal.class));
    }

    @Test
    void deleteMealByNameTest() {
        when(mealRepository.findByName(anyString())).thenReturn(meal);

        mealService.deleteMealByName("Fruit Salad");

        verify(mealRepository, times(1)).findByName(anyString());
        verify(mealRepository, times(1)).delete(any(Meal.class));
    }

    @Test
    void deleteMealByNameNotFoundTest() {
        when(mealRepository.findByName(anyString())).thenReturn(null);

        assertThrows(ResourceNotFoundException.class, () -> mealService.deleteMealByName("Fruit Salad"));
        verify(mealRepository, times(1)).findByName(anyString());
        verify(mealRepository, never()).delete(any(Meal.class));
    }
}
