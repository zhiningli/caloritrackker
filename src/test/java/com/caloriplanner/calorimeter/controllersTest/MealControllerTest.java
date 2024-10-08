package com.caloriplanner.calorimeter.controllersTest;

import com.caloriplanner.calorimeter.clos.constants.FoodCategory;
import com.caloriplanner.calorimeter.clos.controllers.MealController;
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

        // Set up food names and weights as a Map<String, Double>
        Map<String, Double> foodNames = new HashMap<>();
        foodNames.put("Apple", 100.0);
        foodNames.put("Banana", 50.0);

        mealDto = MealDto.builder()
                .name("Fruit Salad")
                .category(FoodCategory.COMPOSITE)
                .foodNames(foodNames)
                .build();
    }

    @Test
    void createMealTest() throws Exception {
        when(mealService.createMeal(any(MealDto.class))).thenReturn(mealDto);

        mockMvc.perform(post("/api/meals")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Fruit Salad\",\"category\":\"COMPOSITE\",\"caloriesPerGram\":0.5,\"proteinsPerGram\":0.02,\"fatsPerGram\":0.01,\"carbsPerGram\":0.13,\"foodNames\":{\"Apple\":100.0,\"Banana\":50.0}}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Fruit Salad"))
                .andExpect(jsonPath("$.category").value("COMPOSITE"))
                .andExpect(jsonPath("$.foodNames.Apple").value(100.0))
                .andExpect(jsonPath("$.foodNames.Banana").value(50.0));
    }

    @Test
    void getAllMealsTest() throws Exception {
        when(mealService.getAllMeal()).thenReturn(List.of(mealDto));

        mockMvc.perform(get("/api/meals")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Fruit Salad"))
                .andExpect(jsonPath("$[0].category").value("COMPOSITE"))
                .andExpect(jsonPath("$[0].foodNames.Apple").value(100.0))
                .andExpect(jsonPath("$[0].foodNames.Banana").value(50.0));
    }

    @Test
    void getMealByIdTest() throws Exception {
        when(mealService.getMealById(anyString())).thenReturn(mealDto);

        mockMvc.perform(get("/api/meals/{id}", "1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Fruit Salad"))
                .andExpect(jsonPath("$.category").value("COMPOSITE"))
                .andExpect(jsonPath("$.foodNames.Apple").value(100.0))
                .andExpect(jsonPath("$.foodNames.Banana").value(50.0));
    }

    @Test
    void updateMealTest() throws Exception {
        when(mealService.updateMeal(any(MealDto.class))).thenReturn(mealDto);

        mockMvc.perform(put("/api/meals/{id}", "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Fruit Salad\",\"category\":\"COMPOSITE\",\"caloriesPerGram\":0.5,\"proteinsPerGram\":0.02,\"fatsPerGram\":0.01,\"carbsPerGram\":0.13,\"foodNames\":{\"Apple\":100.0,\"Banana\":50.0}}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Fruit Salad"))
                .andExpect(jsonPath("$.category").value("COMPOSITE"))
                .andExpect(jsonPath("$.foodNames.Apple").value(100.0))
                .andExpect(jsonPath("$.foodNames.Banana").value(50.0));
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
