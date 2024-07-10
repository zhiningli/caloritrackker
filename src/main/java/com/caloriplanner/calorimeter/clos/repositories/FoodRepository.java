package com.caloriplanner.calorimeter.clos.repositories;


import com.caloriplanner.calorimeter.clos.models.Food;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface FoodRepository extends MongoRepository<Food, String> {
    Food findByName(String name);
    void deleteByName(String name);
}



