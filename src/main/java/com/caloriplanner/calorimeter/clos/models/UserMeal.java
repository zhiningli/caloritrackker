package com.caloriplanner.calorimeter.clos.models;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "user_meals")
public class UserMeal {

    @Id
    @Builder.Default
    private String id = UUID.randomUUID().toString();

    private String mealId;
    private String userId;
}

