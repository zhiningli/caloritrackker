package com.caloriplanner.calorimeter.clos.controllers;


import com.caloriplanner.calorimeter.clos.models.dto.HealthMetricsDto;
import com.caloriplanner.calorimeter.clos.service.HealthMetricsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping
    public ResponseEntity<HealthMetricsDto> getHealthMetrics(@PathVariable String userSlug){
        HealthMetricsDto healthMetrics = healthMetricsService.getHealthMetrics(userSlug);
        return ResponseEntity.ok(healthMetrics);
    }
}
