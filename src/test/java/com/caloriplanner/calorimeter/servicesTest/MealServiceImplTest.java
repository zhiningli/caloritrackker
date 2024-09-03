package com.caloriplanner.calorimeter.servicesTest;

import com.caloriplanner.calorimeter.clos.constants.FoodCategory;
import com.caloriplanner.calorimeter.clos.exceptions.ResourceNotFoundException;
import com.caloriplanner.calorimeter.clos.mapper.MealMapper;
import com.caloriplanner.calorimeter.clos.models.Food;
import com.caloriplanner.calorimeter.clos.models.Meal;
import com.caloriplanner.calorimeter.clos.models.dto.MealDto;
import com.caloriplanner.calorimeter.clos.repositories.FoodRepository;
import com.caloriplanner.calorimeter.clos.repositories.MealRepository;
import com.caloriplanner.calorimeter.clos.service.impl.MealServiceImpl;
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
    private FoodRepository foodRepository;

    @InjectMocks
    private MealServiceImpl mealService;

    private Meal meal;
    private MealDto mealDto;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);

        MealMapper mealMapper = new MealMapper();

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

        Map<Food, Double> foodMap = new HashMap<>();
        foodMap.put(apple, 100.0);
        foodMap.put(banana, 50.0);
        meal = new Meal("Fruit Salad", FoodCategory.FRUIT, foodMap);

        // Create MealDto using Food names and weights
        mealDto = mealMapper.mapToMealDto(meal);
    }

    @Test
    void createMealTest() {
        // Mocking the Food objects that will be returned by the FoodRepository
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

        // Mock the findById calls in foodRepository to return the above Food objects
        when(foodRepository.findById("1")).thenReturn(Optional.of(apple));
        when(foodRepository.findById("2")).thenReturn(Optional.of(banana));

        // Mock saving the Meal
        when(mealRepository.save(any(Meal.class))).thenReturn(meal);

        MealDto savedMealDto = mealService.createMeal(mealDto);

        assertNotNull(savedMealDto);
        assertEquals(mealDto.getName(), savedMealDto.getName());
        assertEquals(mealDto.getFoodNames().size(), savedMealDto.getFoodNames().size());
        assertEquals(mealDto.getFoodNames().get("Apple"), savedMealDto.getFoodNames().get("Apple"));
        verify(mealRepository, times(1)).save(any(Meal.class));
        verify(foodRepository, times(1)).findById("1");
        verify(foodRepository, times(1)).findById("2");
    }

    @Test
    void getAllMealTest() {
        when(mealRepository.findAll()).thenReturn(Arrays.asList(meal));

        List<MealDto> mealDtos = mealService.getAllMeal();

        assertEquals(1, mealDtos.size());
        assertEquals(meal.getName(), mealDtos.get(0).getName());
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
        // Creating food items with valid weights
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

        Map<Food, Double> foodMap = new HashMap<>();

        foodMap.put(apple, 100.0);
        foodMap.put(banana, 50.0);
        meal.setFoods(foodMap);

        // Setting up mocks
        when(foodRepository.findById("1")).thenReturn(Optional.of(apple));
        when(foodRepository.findById("2")).thenReturn(Optional.of(banana));
        when(mealRepository.findById(anyString())).thenReturn(Optional.of(meal));
        when(mealRepository.save(any(Meal.class))).thenReturn(meal);

        // Running the update method
        MealDto updatedMealDto = mealService.updateMeal("1", mealDto);

        // Validating results
        assertNotNull(updatedMealDto);
        assertEquals(mealDto.getName(), updatedMealDto.getName());
        assertEquals(mealDto.getFoodNames().size(), updatedMealDto.getFoodNames().size());
        assertEquals(mealDto.getFoodNames().get("Apple"), updatedMealDto.getFoodNames().get("Apple"));
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
