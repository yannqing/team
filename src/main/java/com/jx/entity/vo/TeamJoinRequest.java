package com.jx.entity.vo;

import lombok.Data;

/*
        teamJoin方法请求参数的包装类
*/
@Data
public class TeamJoinRequest {

    private String password;

    private Long teamId;

}
