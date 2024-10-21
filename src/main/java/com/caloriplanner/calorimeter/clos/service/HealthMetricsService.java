package com.caloriplanner.calorimeter.clos.service;

import com.caloriplanner.calorimeter.clos.models.dto.HealthMetricsDto;

public interface HealthMetricsService {

    public void updateHealthMetrics(HealthMetricsDto healthMetricsDto);
    public HealthMetricsDto getHealthMetrics(String userSlug);
}
