package com.jx.dao.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.math.BigInteger;

@Data
@Accessors(chain = true)
public class UserVO {
    private String avatarUrl;
    private String creatTime;
    private String email;
    private Integer gender;
    private Integer id;
    private String phone;
    private String planetCode;
    private String profile;
    private String tags;
    private String updateTime;
    private String userAccount;
    private Integer userRole;
    private Integer userStatus;
    private String userName;

}
