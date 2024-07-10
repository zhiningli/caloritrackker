package com.caloriplanner.calorimeter.clos.controllerTest;

import com.caloriplanner.calorimeter.clos.constants.FoodCategory;
import com.caloriplanner.calorimeter.clos.controllers.FoodController;
import com.caloriplanner.calorimeter.clos.models.Food;
import com.caloriplanner.calorimeter.clos.service.FoodService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class FoodControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private FoodService foodService;

    @InjectMocks
    private FoodController foodController;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetFoodByName() throws Exception {
        Food apple = Food.builder()
                .name("Apple")
                .category(FoodCategory.FRUIT)
                .caloriesPerGram(0.52)
                .proteinsPerGram(0.03)
                .fatsPerGram(0.002)
                .carbsPerGram(0.14)
                .weight(150)
                .build();

        when(foodService.getFoodByName("Apple")).thenReturn(apple);

        mockMvc.perform(get("/api/foods/Apple")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Apple"));
    }

    @Test
    void testCreateFood() throws Exception {
        Food apple = Food.builder()
                .name("Apple")
                .category(FoodCategory.FRUIT)
                .caloriesPerGram(0.52)
                .proteinsPerGram(0.03)
                .fatsPerGram(0.002)
                .carbsPerGram(0.14)
                .weight(150)
                .build();
        when(foodService.createFood(any(Food.class))).thenReturn(apple);

        mockMvc.perform(post("/api/foods/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Apple\",\"category\":\"FRUIT\",\"caloriesPerGram\":0.52,\"proteinsPerGram\":0.03,\"fatsPerGram\":0.002,\"carbsPerGram\":0.14,\"weight\":150}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Apple"));
    }
}