package com.caloriplanner.calorimeter.clos.service.impl;

import com.caloriplanner.calorimeter.clos.models.User;
import com.caloriplanner.calorimeter.clos.models.dto.HealthMetricsDto;
import com.caloriplanner.calorimeter.clos.repositories.UserRepository;
import com.caloriplanner.calorimeter.clos.service.HealthMetricsService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

public class HealthMetricsServiceImpl implements HealthMetricsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void updateHealthMetrics(HealthMetricsDto healthMetricsDto) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userName = userDetails.getUsername();

        User user = userRepository.findByUsername(userName);

        user.setHealthMetrics(healthMetricsDto);
        userRepository.save(user);
    }
}
