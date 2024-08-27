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

@Service
public class MealServiceImpl implements MealService {

    @Autowired
    MealRepository mealRepository;

    @Override
    @Transactional
    public MealDto createMeal(MealDto mealDto) {
        Meal meal = MealMapper.mapToMeal(mealDto);
        mealRepository.save(meal);
        return MealMapper.mapToMealDto(meal);
    }

    @Override
    @Transactional
    public List<MealDto> getAllMeal() {
        List<Meal> meals = mealRepository.findAll();
        return meals.stream().map(MealMapper::mapToMealDto).toList();
    }

    @Override
    public MealDto getMealById(String id){
        Meal meal = mealRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("No meal with id "+id+" found!"));
        return MealMapper.mapToMealDto(meal);
    }

    @Override
    public MealDto getMealByName(String name) {
        Meal meal = mealRepository.findByName(name);
        if (meal != null){
            return MealMapper.mapToMealDto(meal);
        } else {
            throw new ResourceNotFoundException("No meal with name " + name + " found!");
        }
    }

    @Override
    public MealDto updateMeal(String id, MealDto mealDto) {
        Meal meal= mealRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("No meal with id " + id + "found! Unable to update meal.")
        );

        meal.setName(mealDto.getName());
        meal.setCategory(mealDto.getCategory());
        meal.setCaloriesPerGram(mealDto.getCaloriesPerGram());
        meal.setProteinsPerGram(mealDto.getProteinsPerGram());
        meal.setFatsPerGram(mealDto.getFatsPerGram());
        meal.setCarbsPerGram(mealDto.getCarbsPerGram());
        Meal updatedMeal = mealRepository.save(meal);
        return MealMapper.mapToMealDto(updatedMeal);
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
}
