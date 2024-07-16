package com.example.starbucks.model;

import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor      //new Coffee()
@AllArgsConstructor     //new Coffee(id,name,price)
@RequiredArgsConstructor//new Coffee(name,price) by NonNull
@Entity
@Table(name="user")
public class UserCustom {
    @Id
    @GeneratedValue(strategy =GenerationType.IDENTITY)
    private int id;

    //Id만 붙여서하는게 안되서 컬럼명을 _로 이어지게 하는 편이 낫다
    //유독 id만 그러니 주의할것
    @NonNull
    @Column(name="user_id")
    private String userId;

    @NonNull
    @Column(name="password")
    private String password;

    @NonNull
    @Column(name="nickname")
    private String nickname;


}
