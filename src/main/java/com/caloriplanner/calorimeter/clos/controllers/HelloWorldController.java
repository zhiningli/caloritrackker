package com.caloriplanner.calorimeter.clos.controllers;

import com.caloriplanner.calorimeter.clos.service.HelloWorldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {

    @Autowired
    private HelloWorldService helloWorldService;

    @GetMapping("/hello")
    public String hello(){
        return helloWorldService.getHelloMessage();
    }
}
