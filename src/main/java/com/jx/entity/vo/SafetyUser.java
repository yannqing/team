package com.jx.entity.vo;

import lombok.Data;

/*
        删除密码等敏感属性的UserVO封装类
*/
@Data
public class SafetyUser {

    private String avatarUrl;

    private String creatTime;

    private String email;

    private Integer gender;

    private Long id;

    private String phone;

    private String planetCode;

    private String profile;

    private String tags;

    private String updateTime;

    private String userAccount;

    private Integer userRole;

    private Integer userStatus;

    private String username;

    private Integer isDelete;

    private String userPassword;

}
