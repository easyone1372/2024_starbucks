package com.example.starbucks.service;

import com.example.starbucks.model.Coffee;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

// 계약[약속]
public interface CoffeeService {
    List<Coffee> getAllCoffees();
    List<Coffee> getCoffeesByName(String name);
    List<Coffee> getCoffeeByPrice(int min, int max);
    String addCoffee(Coffee coffee);
}
