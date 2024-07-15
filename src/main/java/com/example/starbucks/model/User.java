package com.example.starbucks.model;

import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor      //new Coffee()
@AllArgsConstructor     //new Coffee(id,name,price)
@RequiredArgsConstructor//new Coffee(name,price) by NonNull
@Entity
@Table(name="user")
public class User {
    @Id
    @GeneratedValue(strategy =GenerationType.IDENTITY)
    private int id;

    @NonNull
    @Column(name="userId")
    private String userId;

    @NonNull
    @Column(name="password")
    private String password;

    @NonNull
    @Column(name="nickname")
    private String nickname;


}
