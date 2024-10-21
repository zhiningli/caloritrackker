package com.caloriplanner.calorimeter.clos.controllers;


import com.caloriplanner.calorimeter.clos.models.dto.HealthMetricsDto;
import com.caloriplanner.calorimeter.clos.service.HealthMetricsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/health")
public class HealthMetricsController {

    @Autowired
    private HealthMetricsService healthMetricsService;

    @PostMapping("/update")
    public ResponseEntity<String> updateHealthMetrics(@RequestBody HealthMetricsDto healthMetricsDto){
        healthMetricsService.updateHealthMetrics(healthMetricsDto);
        return ResponseEntity.ok("Health metrics updated successfully.");
    }
}
