package com.caloriplanner.calorimeter.clos.models.dto;

import lombok.*;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HealthMetricsDto {
    private double height;
    private double weight;
    private int yearOfBirth;
    private double bodyFatPercentage;
    private String biologicalSex;
    private double bmr;
}
