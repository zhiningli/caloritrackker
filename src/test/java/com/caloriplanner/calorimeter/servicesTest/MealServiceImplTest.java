package com.caloriplanner.calorimeter.servicesTest;

import com.caloriplanner.calorimeter.clos.constants.FoodCategory;
import com.caloriplanner.calorimeter.clos.exceptions.ResourceNotFoundException;
import com.caloriplanner.calorimeter.clos.mapper.MealMapper;
import com.caloriplanner.calorimeter.clos.models.Food;
import com.caloriplanner.calorimeter.clos.models.Meal;
import com.caloriplanner.calorimeter.clos.models.dto.MealDto;
import com.caloriplanner.calorimeter.clos.repositories.MealRepository;
import com.caloriplanner.calorimeter.clos.service.impl.MealServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;


public class MealServiceImplTest {

    @Mock
    private MealRepository mealRepository;

    @InjectMocks
    private MealServiceImpl mealService;

    private Meal meal;
    private MealDto mealDto;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        Food apple = new Food();
        apple.setName("Apple");
        apple.setCategory(FoodCategory.FRUIT);
        apple.setCaloriesPerGram(0.52);
        apple.setProteinsPerGram(0.03);
        apple.setFatsPerGram(0.002);
        apple.setCarbsPerGram(0.14);
        apple.setWeight(150);

        Food banana = new Food();
        banana.setName("Banana");
        banana.setCategory(FoodCategory.FRUIT);
        banana.setCaloriesPerGram(0.89);
        banana.setProteinsPerGram(0.011);
        banana.setFatsPerGram(0.003);
        banana.setCarbsPerGram(0.23);
        banana.setWeight(120);

        List<Food> foodList = Arrays.asList(apple, banana);
        meal = new Meal(foodList);
        meal.setId("1");
        meal.setName("Fruit Salad");

        mealDto = MealMapper.mapToMealDto(meal);
    }

    @Test
    void createMealTest() {
        when(mealRepository.save(any(Meal.class))).thenReturn(meal);

        MealDto savedMealDto = mealService.createMeal(mealDto);

        assertNotNull(savedMealDto);
        assertEquals(mealDto.getName(), savedMealDto.getName());
        verify(mealRepository, times(1)).save(any(Meal.class));
    }

    @Test
    void getAllMealTest() {
        when(mealRepository.findAll()).thenReturn(Arrays.asList(meal));

        List<MealDto> mealDtos = mealService.getAllMeal();

        assertEquals(1, mealDtos.size());
        verify(mealRepository, times(1)).findAll();
    }

    @Test
    void getMealByIdNotFoundTest() {
        when(mealRepository.findById(anyString())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> mealService.getMealById("1"));
        verify(mealRepository, times(1)).findById(anyString());
    }

    @Test
    void getMealByNameTest() {
        when(mealRepository.findByName(anyString())).thenReturn(meal);

        MealDto foundMealDto = mealService.getMealByName("Fruit Salad");

        assertNotNull(foundMealDto);
        assertEquals(meal.getName(), foundMealDto.getName());
        verify(mealRepository, times(1)).findByName(anyString());
    }

    @Test
    void getMealByNameNotFoundTest() {
        when(mealRepository.findByName(anyString())).thenReturn(null);

        assertThrows(ResourceNotFoundException.class, () -> mealService.getMealByName("Fruit Salad"));
        verify(mealRepository, times(1)).findByName(anyString());
    }

    @Test
    void updateMealTest() {
        when(mealRepository.findById(anyString())).thenReturn(Optional.of(meal));
        when(mealRepository.save(any(Meal.class))).thenReturn(meal);

        MealDto updatedMealDto = mealService.updateMeal("1", mealDto);

        assertNotNull(updatedMealDto);
        assertEquals(mealDto.getName(), updatedMealDto.getName());
        verify(mealRepository, times(1)).findById(anyString());
        verify(mealRepository, times(1)).save(any(Meal.class));
    }

    @Test
    void updateMealNotFoundTest() {
        when(mealRepository.findById(anyString())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> mealService.updateMeal("1", mealDto));
        verify(mealRepository, times(1)).findById(anyString());
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
    }
}
