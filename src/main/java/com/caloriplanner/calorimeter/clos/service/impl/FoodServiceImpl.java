package com.caloriplanner.calorimeter.clos.service.impl;

import com.caloriplanner.calorimeter.clos.exceptions.ResourceNotFoundException;
import com.caloriplanner.calorimeter.clos.mapper.FoodMapper;
import com.caloriplanner.calorimeter.clos.models.Food;
import com.caloriplanner.calorimeter.clos.models.dto.FoodDto;
import com.caloriplanner.calorimeter.clos.repositories.FoodRepository;
import com.caloriplanner.calorimeter.clos.service.FoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class FoodServiceImpl implements FoodService {

    @Autowired
    private FoodRepository foodRepository;

    @Override
    @Transactional
    public FoodDto createFood(FoodDto foodDto) {
        Food food = FoodMapper.mapToFood(foodDto);
        Food savedFood = foodRepository.save(food);
        return FoodMapper.mapToFoodDto(savedFood);
    }

    @Override
    @Transactional(readOnly = true)
    public List<FoodDto> getAllFoods() {
        List<Food> foods = foodRepository.findAll();
        return foods.stream().map(FoodMapper::mapToFoodDto).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public FoodDto getFoodById(String id) {
        Food food = foodRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Food not found with id: " + id));
        return FoodMapper.mapToFoodDto(food);
    }

    @Override
    @Transactional(readOnly = true)
    public FoodDto getFoodByName(String name) {
        Food food = foodRepository.findByName(name);
        if (food != null) {
            return FoodMapper.mapToFoodDto(food);
        } else {
            throw new ResourceNotFoundException("Food not found with name: " + name);
        }
    }

    @Override
    @Transactional
    public void deleteFood(String id) {  // Update to delete by UUID
        Food food = foodRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Food not found with id: " + id));
        foodRepository.delete(food);
    }
}
