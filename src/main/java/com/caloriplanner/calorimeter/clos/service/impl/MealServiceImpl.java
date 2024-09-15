package com.caloriplanner.calorimeter.clos.service.impl;

import com.caloriplanner.calorimeter.clos.exceptions.ResourceNotFoundException;
import com.caloriplanner.calorimeter.clos.mapper.MealMapper;
import com.caloriplanner.calorimeter.clos.models.Meal;
import com.caloriplanner.calorimeter.clos.models.dto.MealDto;
import com.caloriplanner.calorimeter.clos.repositories.MealRepository;
import com.caloriplanner.calorimeter.clos.service.MealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MealServiceImpl implements MealService {

    @Autowired
    MealRepository mealRepository;

    @Autowired
    MealMapper mealMapper;

    @Override
    @Transactional
    public MealDto createMeal(MealDto mealDto) {
        Meal meal = mealMapper.mapToMeal(mealDto);
        mealRepository.save(meal);
        return mealMapper.mapToMealDto(meal);
    }

    @Override
    @Transactional
    public List<MealDto> getAllMeal() {
        List<Meal> meals = mealRepository.findAll();
        return meals.stream().map(mealMapper::mapToMealDto).toList();
    }

    @Override
    public MealDto getMealById(String id){
        Meal meal = mealRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("No meal with id "+id+" found!"));
        return mealMapper.mapToMealDto(meal);
    }

    @Override
    public MealDto getMealByName(String name) {
        Meal meal = mealRepository.findByName(name);
        if (meal != null){
            return mealMapper.mapToMealDto(meal);
        } else {
            throw new ResourceNotFoundException("No meal with name " + name + " found!");
        }
    }

    @Override
    public MealDto updateMeal(MealDto mealDto){

        Meal oldMeal = mealRepository.findById(mealDto.getId()).orElseThrow(
                () -> new ResourceNotFoundException("Error updating meals")
        );
        mealRepository.delete(oldMeal);
        return createMeal(mealDto);
    }

    @Override
    public void deleteMealById(String id) {
        Meal meal = mealRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Unable to find meal with id " + id + ", unable to perform deletion"));
        mealRepository.delete(meal);
    }

    @Override
    public void deleteMealByName(String name) {
        Meal meal = mealRepository.findByName(name);
        if (meal != null){
            mealRepository.delete(meal);
        } else {
            throw new ResourceNotFoundException("Unable to find meal with name " + name +", unable to perform deletion");
        }
    }

    @Override
    public void deleteMealsByBatch(List<MealDto> mealDtoList) {
        try {
            List<Meal> mealList = mealDtoList.stream()
                    .map(mealDto -> mealMapper.mapToMeal(mealDto))
                    .collect(Collectors.toList());
            System.out.println(mealList);
            mealRepository.deleteAll(mealList);
        } catch (Exception e) {
            throw new RuntimeException("Failed to delete meals by batch", e);
        }
    }
}
