package com.example.starbucks.token;

import com.example.starbucks.model.UserCustom;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtUtil {

    private static final String SECRET_KEY="IwannaGoHomeRightNowIwannaGoHomeRightNowIwannaGoHomeRightNow";  //키 완전 길게 작성하기
    private static final SecretKey key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));

    public static String generateToken(UserCustom userCustom){

        return Jwts
                .builder()
                .issuedAt(new Date(System.currentTimeMillis())) //발급시간
                .expiration(new Date(System.currentTimeMillis()+1000*5)) //기한 시간
                .subject(userCustom.getUserId())
                .claim("userId",userCustom.getUserId())
                .signWith(key)
                .compact();
    }

    public static Boolean ValidToken(String token){
        try {
            Jwts
                    .parser()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
            return true;
        }catch(Exception e){
            return false;
        }
    }
    public static Claims extractToken(String token){
        return Jwts
                .parser()                   //해석
                .verifyWith(key)            //키 제공
                .build()                    //빌드
                .parseSignedClaims(token)   //토큰 제공 후 claim 해석
                .getPayload();              //payload 제공
    }
}
