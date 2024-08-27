package com.caloriplanner.calorimeter.clos.models;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DBRef;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "user_meals")
public class UserMeal {
    @Id
    private String id;

    @DBRef
    private Meal meal;  // Reference to the shared Meal object

    private String userId;  // Reference to the user
    private double weight;  // User-specific weight

}

