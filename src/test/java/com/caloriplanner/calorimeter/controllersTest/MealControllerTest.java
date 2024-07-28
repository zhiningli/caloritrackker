package com.caloriplanner.calorimeter.controllersTest;

import com.caloriplanner.calorimeter.clos.constants.FoodCategory;
import com.caloriplanner.calorimeter.clos.controllers.MealController;
import com.caloriplanner.calorimeter.clos.mapper.MealMapper;
import com.caloriplanner.calorimeter.clos.models.Food;
import com.caloriplanner.calorimeter.clos.models.Meal;
import com.caloriplanner.calorimeter.clos.models.dto.MealDto;
import com.caloriplanner.calorimeter.clos.service.MealService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class MealControllerTest {

    private MockMvc mockMvc;

    @Mock
    private MealService mealService;

    @InjectMocks
    private MealController mealController;

    private MealDto mealDto;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(mealController).build();

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
        Meal meal = new Meal(foodList);
        meal.setId("1");
        meal.setName("Fruit Salad");
        meal.setCategory(FoodCategory.COMPOSITE);

        mealDto = MealMapper.mapToMealDto(meal);
    }

    @Test
    void createMealTest() throws Exception {
        when(mealService.createMeal(any(MealDto.class))).thenReturn(mealDto);

        mockMvc.perform(post("/api/meals")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Fruit Salad\",\"category\":\"COMPOSITE\",\"caloriesPerGram\":0.5,\"proteinsPerGram\":0.02,\"fatsPerGram\":0.01,\"carbsPerGram\":0.13,\"weight\":200,\"foods\":[{\"name\":\"Apple\",\"category\":\"FRUIT\",\"caloriesPerGram\":0.52,\"proteinsPerGram\":0.03,\"fatsPerGram\":0.002,\"carbsPerGram\":0.14,\"weight\":150},{\"name\":\"Banana\",\"category\":\"FRUIT\",\"caloriesPerGram\":0.89,\"proteinsPerGram\":0.011,\"fatsPerGram\":0.003,\"carbsPerGram\":0.23,\"weight\":120}]}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Fruit Salad"))
                .andExpect(jsonPath("$.category").value("COMPOSITE")); // Compare the string value directly
    }

    @Test
    void getAllMealsTest() throws Exception {
        when(mealService.getAllMeal()).thenReturn(Arrays.asList(mealDto));

        mockMvc.perform(get("/api/meals")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Fruit Salad"))
                .andExpect(jsonPath("$[0].category").value("COMPOSITE"));
    }

    @Test
    void getMealByIdTest() throws Exception {
        when(mealService.getMealById(anyString())).thenReturn(mealDto);

        mockMvc.perform(get("/api/meals/{id}", "1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Fruit Salad"))
                .andExpect(jsonPath("$.category").value("COMPOSITE"));
    }

    @Test
    void updateMealTest() throws Exception {
        when(mealService.updateMeal(anyString(), any(MealDto.class))).thenReturn(mealDto);

        mockMvc.perform(put("/api/meals/{id}", "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Fruit Salad\",\"category\":\"FRUIT\",\"caloriesPerGram\":0.5,\"proteinsPerGram\":0.02,\"fatsPerGram\":0.01,\"carbsPerGram\":0.13,\"weight\":200,\"foods\":[{\"name\":\"Apple\",\"category\":\"FRUIT\",\"caloriesPerGram\":0.52,\"proteinsPerGram\":0.03,\"fatsPerGram\":0.002,\"carbsPerGram\":0.14,\"weight\":150},{\"name\":\"Banana\",\"category\":\"FRUIT\",\"caloriesPerGram\":0.89,\"proteinsPerGram\":0.011,\"fatsPerGram\":0.003,\"carbsPerGram\":0.23,\"weight\":120}]}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Fruit Salad"))
                .andExpect(jsonPath("$.category").value("COMPOSITE"));
    }

    @Test
    void deleteMealByIdTest() throws Exception {
        doNothing().when(mealService).deleteMealById(anyString());

        mockMvc.perform(delete("/api/meals/{id}", "1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("Meal deleted successfully!"));

        verify(mealService, times(1)).deleteMealById(anyString());
    }
}
