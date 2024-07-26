package com.caloriplanner.calorimeter.clos.repositories;

import com.caloriplanner.calorimeter.clos.models.Food;
import com.caloriplanner.calorimeter.clos.models.Meal;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MealRepository extends MongoRepository<Meal, String> {
    Meal findByName(String name);
    void deleteByName(String name);
}
