package com.example.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

public class MyController {

    @GetMapping("/hello")
    public String sayHello() {
        System.out.println("Hello end pont was hit!");
        return "Hello, World!";
    }
}

