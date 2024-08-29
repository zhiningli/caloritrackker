package com.caloriplanner.calorimeter.clos.serializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import java.io.IOException;
import com.caloriplanner.calorimeter.clos.constants.FoodCategory;

public class FoodCategoryDeserializer extends JsonDeserializer<FoodCategory> {

    @Override
    public FoodCategory deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        String value = p.getText();
        for (FoodCategory category : FoodCategory.values()) {
            if (category.getValue().equalsIgnoreCase(value)) {
                return category;
            }
        }
        throw new IllegalArgumentException("Unknown value: " + value);
    }
}