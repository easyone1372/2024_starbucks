package com.example.starbucks.service;

import com.example.starbucks.model.Coffee;
import com.example.starbucks.status.ResultStatus;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

// 계약[약속]
public interface CoffeeService {
    List<Coffee> getAllCoffees();
    List<Coffee> getCoffeesByName(String name);
    List<Coffee> getCoffeesByPrice(int min, int max);
    ResultStatus addCoffee(Coffee coffee);
    Optional<Coffee> getCoffeeById(Integer id);
}
