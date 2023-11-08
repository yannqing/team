package com.jx.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeamUserVO {

    private String createTime;
    private String description;
    private String expireTime;
    private boolean hasJoin;
    private Integer hasJoinNum;
    private Integer id;
    private Integer maxNum;
    private String name;
    private String status;
    private String updateTime;
    private Integer userId;
    private List<UserVO> userVO;
}
