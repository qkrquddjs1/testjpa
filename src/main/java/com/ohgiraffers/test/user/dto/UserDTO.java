package com.ohgiraffers.test.user.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserDTO {
    private int userNo;
    private String userId;
    private String userPwd;
    private String userName;
    private String gender;
    private String phone;
    private String email;
}
