package com.example.starbucks.controller;

import com.example.starbucks.db.ApiResponse;
import com.example.starbucks.model.UserCustom;
import com.example.starbucks.service.UserDetailServiceImpl;
import com.example.starbucks.service.UserService;
import com.example.starbucks.status.ResponseStatus;
import com.example.starbucks.status.ResultStatus;
import com.example.starbucks.token.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;


@RestController

@RequestMapping("api/v1/user")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    UserDetailServiceImpl userDetailService;

    //라이브러리임
    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<String>> registerUser(@RequestBody UserCustom userCustom){
        // userId, password, nickname
        // 아이디 유효성 검사[0], 중복조회[0], 비밀번호 암호화[?]

        //비밀번호 암호화 코드
        userCustom.setPassword(passwordEncoder.encode(userCustom.getPassword()));

        //저장
        userService.saveUser(userCustom);
        ApiResponse apiResponse = new ApiResponse(ResponseStatus.SUCCESS,"성공",null);
        return ResponseEntity.ok(apiResponse);
   }

   @PostMapping("/login")
    public ResponseEntity<ApiResponse<String>> login(@RequestBody UserCustom userCustom) {
       //id와 pw를 체크해주는 security 라이브러리 활용해야함
       try {
           //userDetail에서 제공한 클래스
           // db에서 쉽게 해당 id 가져오는 역할
           UserDetails userDetails = userDetailService.loadUserByUsername(userCustom.getUserId());

           // 가져온 id가 null이거나 post로 보낸 비번이랑 일치하지 않으면 if 실행
           if (userDetails == null || !passwordEncoder.matches(userCustom.getPassword(), userDetails.getPassword())) {
               throw new BadCredentialsException("아이디가 존재하지 않거나 비밀번호가 틀렸습니다.");
           }

           //성공
           //토큰 발급
           String token = JwtUtil.generateToken(userCustom);

           //response header
           HttpHeaders httpHeaders = new HttpHeaders();
           httpHeaders.set("Authorization","Bearer"+token);

           //response body
           ApiResponse apiResponse = new ApiResponse(ResponseStatus.SUCCESS, "성공", null);
           return ResponseEntity.ok().headers(httpHeaders).body(apiResponse);


       } catch (UsernameNotFoundException | BadCredentialsException e) {
           System.out.println("아이디가 존재하지 않습니다.");
           ApiResponse apiResponse = new ApiResponse(ResponseStatus.UNAUTHORIZED, "승인 불가", null);
           return ResponseEntity.ok(apiResponse);
       }

   }



    public ApiResponse<?> validateApiResponse(ResultStatus status){
        ResponseStatus resultStatus = ResultStatus.FAIL.equals(status) ? ResponseStatus.FAIL : ResponseStatus.SUCCESS;
        String message = ResultStatus.FAIL.equals(status) ? "실패 했음" : "성공 했음";
        return new ApiResponse(resultStatus, message,null);
    }
}

