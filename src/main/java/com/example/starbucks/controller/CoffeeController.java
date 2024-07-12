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

import javax.xml.transform.Result;
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
        Boolean isEquals = ResultStatus.FAIL.equals(result);
        if(isEquals) {
            return ResponseEntity.ok(new ApiResponse(ResponseStatus.FAIL,"실패",null));
        }
        else{
            return ResponseEntity.ok(new ApiResponse(ResponseStatus.SUCCESS,"성공",null ));
        }

//        if(ResultStatus.FAIL.equals(result)){
////             ApiResponse<String> apiResponse = new ApiResponse<>(ResponseStatus.FAIL,"실패",null);
////            ApiResponse apiResponse = new ApiResponse(ResponseStatus.FAIL,"실패",null);
//        return ResponseEntity.ok(new ApiResponse(ResponseStatus.FAIL,"실패",null));
//
//        }else{
////            ApiResponse<String> apiResponse = new ApiResponse<>(ResponseStatus.SUCCESS,"저장됨",null);
////            return ResponseEntity.ok(apiResponse);
////            ApiResponse apiResponse = new ApiResponse(ResponseStatus.SUCCESS,"성공",null );
//            return ResponseEntity.ok(new ApiResponse(ResponseStatus.SUCCESS,"성공",null ));
//        }
    }

    //? wildcard -> any
    public ApiResponse<?> validateApiResponse(ResultStatus status){
        ResponseStatus resultStatus = ResultStatus.FAIL.equals(status) ? ResponseStatus.FAIL : ResponseStatus.SUCCESS;
        String message = ResultStatus.FAIL.equals(status) ? "실패 했음" : "성공 했음";
        return new ApiResponse(resultStatus, message,null);
//        두 데이터를 하나의 클래스로 만들어서 도출하는 식으로 만들 수 있음
    }


    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<Coffee>>> getCoffeesByName(@RequestParam String name) {
        List<Coffee> coffeeList = coffeeService.getCoffeesByName(name);
       return ResponseEntity.ok(new ApiResponse<>(ResponseStatus.SUCCESS,"성공",coffeeList ));
    }


    //api/v1/coffee/coffeePrice?min=0&max=10000
//    @GetMapping("/price")
//    public List<Coffee> getCoffeesByPrice(@RequestParam int min, @RequestParam int max) {
//        return coffeeService.getCoffeesByPrice(min,max);
//    }

    @GetMapping("/price")
    public ResponseEntity<ApiResponse<List<Coffee>>> getCoffeesByPrice(@RequestParam int min, @RequestParam int max) {
        List<Coffee> coffeeList = coffeeService.getCoffeesByPrice(min,max);
        return ResponseEntity.ok(new ApiResponse<>(ResponseStatus.SUCCESS,"성공",coffeeList ));
    }

    //api/v1/coffee/130
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Optional<Coffee>>> getCoffeeById(@PathVariable Integer id){
        Optional<Coffee> coffee = coffeeService.getCoffeeById(id);
      return ResponseEntity.ok(new ApiResponse<>(ResponseStatus.SUCCESS,"성공",coffee ));

    }
}
