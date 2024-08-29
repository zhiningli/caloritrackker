package com.caloriplanner.calorimeter.clos.constants;

import com.caloriplanner.calorimeter.clos.serializer.FoodCategoryDeserializer;
import lombok.Getter;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(using = FoodCategoryDeserializer.class)
@Getter
public enum FoodCategory {
    FRUIT("fruit"),
    VEGETABLE("vegetable"),
    PROTEIN("protein"),
    DAIRY("dairy"),
    GRAIN("grain"),
    COMPOSITE("composite"),
    OTHER("other");

    private final String value;

    FoodCategory(String value) {
        this.value = value;
    }

}


