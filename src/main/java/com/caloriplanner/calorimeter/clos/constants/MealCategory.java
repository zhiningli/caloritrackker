package com.caloriplanner.calorimeter.clos.constants;

import com.caloriplanner.calorimeter.clos.serializer.MealCategoryDeserializer;
import lombok.Getter;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(using = MealCategoryDeserializer.class)
@Getter
public enum MealCategory {
    BREAKFAST("breakfast"),
    BRUNCH("brunch"),
    LUNCH("lunch"),
    DRINK("drink"),
    SNACK("snack"),
    OTHER("other"),
    DINNER("dinner"),
    SUPPER("supper"),
    COMPOSITE("composite");

    private final String value;

    MealCategory(String value) {
        this.value = value;
    }

}