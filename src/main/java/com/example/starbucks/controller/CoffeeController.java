package com.example.starbucks.controller;

import com.example.starbucks.db.ApiResponse;
import com.example.starbucks.repository.CoffeeRepository;
import com.example.starbucks.model.Coffee;
import com.example.starbucks.service.CoffeeService;
import com.example.starbucks.status.ResponseStatus;
import com.example.starbucks.status.ResultStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


// http: request & response
// request: 메소드[get/post/put/delete/...]

// controller[경로 잡아주기]
// repository[db 데이터 가져오기]


@RestController
//localhost:8080/api/v1/coffee
@RequestMapping("api/v1/coffee")
public class CoffeeController {

    @Autowired
    CoffeeService coffeeService;

    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<Coffee>>> getAllCoffees() {
        List<Coffee> coffeeList = coffeeService.getAllCoffees();
        ApiResponse<List<Coffee>> apiResponse = new ApiResponse<>(ResponseStatus.SUCCESS, "성공", coffeeList);
        return ResponseEntity.ok(apiResponse);
    }


    @PostMapping("/add")
    public ResponseEntity<ApiResponse<String>> addCoffee(@RequestBody Coffee coffee) {
        ResultStatus result = coffeeService.addCoffee(coffee);
        if(result.getResult().equals(ResultStatus.FAIL)){
             ApiResponse<String> apiResponse = new ApiResponse<>(ResponseStatus.FAIL,"실패",null);
             return ResponseEntity.ok(apiResponse);
        }else{
            ApiResponse<String> apiResponse = new ApiResponse<>(ResponseStatus.SUCCESS,"저장됨",null);
            return ResponseEntity.ok(apiResponse);
        }
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<Coffee>>> getCoffeeByName(@RequestParam String name) {
        List<Coffee> coffeeList = coffeeService.getCoffeesByName(name);
        ApiResponse<List<Coffee>> apiResponse = new ApiResponse<>(ResponseStatus.SUCCESS,"성공",coffeeList);
        return ResponseEntity.ok(apiResponse);
    }

    //api/v1/coffee/coffeePrice?min=0&max=10000
    @GetMapping("/coffeePrice")
    public List<Coffee> getCoffeeByPrice(@RequestParam int min, @RequestParam int max) {
        return coffeeService.getCoffeeByPrice(min,max);
    }

    //api/v1/coffee/130
    @GetMapping("{id}")
    public ResponseEntity<ApiResponse<Coffee>> getCoffeeById(@PathVariable Integer id){
        Optional<Coffee> coffee = coffeeService.getCoffeeById(id);
        if(coffee.isEmpty()){
            ApiResponse<Coffee> apiResponse = new ApiResponse<>(ResponseStatus.NOT_FOUND,"정보 없음",null);
            return ResponseEntity.ok(apiResponse);
        }
        else{
            ApiResponse<Coffee> apiResponse = new ApiResponse<>(ResponseStatus.SUCCESS,"정보 존재",coffee.get());
            return ResponseEntity.ok(apiResponse);
        }

    }
}
