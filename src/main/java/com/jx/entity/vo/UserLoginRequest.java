package com.jx.entity.vo;

import lombok.Data;

/*
        userLogin方法请求的包装类
*/
@Data
public class UserLoginRequest {

    private String userAccount;

    private String userPassword;

}
