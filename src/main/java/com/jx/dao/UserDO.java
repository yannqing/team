package com.jx.dao;

import lombok.Getter;
import lombok.Setter;

/**
 * @author 筱锋xiao_lfeng
 * @version v0.0.1
 */
@Getter
@Setter
public class UserDO extends PublicEntityDO {
    private String username;
    private String userAccount;
    private String avatarUrl;
    private Integer gender;
    private String profile;
    private String userPassword;
    private String phone;
    private String email;
    private String tags;
    private Integer userStats;
    private int userRole;
    private String planetCode;
}
