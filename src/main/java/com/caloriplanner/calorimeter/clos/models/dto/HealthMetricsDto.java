package com.caloriplanner.calorimeter.clos.models.dto;

import lombok.*;
import com.caloriplanner.calorimeter.clos.constants.Sex;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HealthMetricsDto {
    private double height;
    private double weight;
    private int yearOfBirth;
    private double bodyFatPercentage;
    private Sex biologicalSex;
    private double bmr;

}
