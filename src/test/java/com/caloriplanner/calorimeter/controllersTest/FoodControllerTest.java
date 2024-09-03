package com.caloriplanner.calorimeter.controllersTest;

import com.caloriplanner.calorimeter.clos.constants.FoodCategory;
import com.caloriplanner.calorimeter.clos.controllers.FoodController;
import com.caloriplanner.calorimeter.clos.models.dto.FoodDto;
import com.caloriplanner.calorimeter.clos.service.FoodService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class FoodControllerTest {

    private MockMvc mockMvc;

    @Mock
    private FoodService foodService;

    @InjectMocks
    private FoodController foodController;

    private FoodDto foodDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(foodController).build();

        foodDto = new FoodDto();
        foodDto.setName("Apple");
        foodDto.setCategory(FoodCategory.FRUIT);
        foodDto.setCaloriesPerGram(0.52);
        foodDto.setProteinsPerGram(0.03);
        foodDto.setFatsPerGram(0.002);
        foodDto.setCarbsPerGram(0.14);
    }

    @Test
    void createFood() throws Exception {
        when(foodService.createFood(any(FoodDto.class))).thenReturn(foodDto);

        mockMvc.perform(post("/api/foods/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Apple\",\"category\":\"FRUIT\",\"caloriesPerGram\":0.52,\"proteinsPerGram\":0.03,\"fatsPerGram\":0.002,\"carbsPerGram\":0.14,\"weight\":150}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Apple"));

        verify(foodService, times(1)).createFood(any(FoodDto.class));
    }

    @Test
    void getAllFood() throws Exception {
        when(foodService.getAllFoods()).thenReturn(Arrays.asList(foodDto));

        mockMvc.perform(get("/api/foods")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Apple"));

        verify(foodService, times(1)).getAllFoods();
    }

    @Test
    void getFoodByName() throws Exception {
        when(foodService.getFoodByName(anyString())).thenReturn(foodDto);

        mockMvc.perform(get("/api/foods/Apple")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Apple"));

        verify(foodService, times(1)).getFoodByName(anyString());
    }

    @Test
    void deleteFood() throws Exception {
        doNothing().when(foodService).deleteFood(anyString());

        mockMvc.perform(delete("/api/foods/Apple")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("Food deleted successfully!"));

        verify(foodService, times(1)).deleteFood(anyString());
    }
}

