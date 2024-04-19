package com.ohgiraffers.test.user.entity;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity         // 엔티티 설정
@Table(name = "user_notnull") // DB 테이블 지정
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 생성자 접근제한
public class User {

    @Id     //엔티티 설정
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userNo;
    private String userId;
    private String userPwd;
    private String userName;
    private String gender;
    private String phone;
    private String email;

    public void modifyUserName(String userName) {this.userName = userName;
    }
}
