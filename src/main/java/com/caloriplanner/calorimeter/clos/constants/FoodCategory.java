package com.caloriplanner.calorimeter.clos.constants;

import lombok.Getter;

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


