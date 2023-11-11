package com.jx.dao.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class TeamUserVO {

    private String createTime;
    private String description;
    private String expireTime;
    private boolean hasJoin;
    private Integer hasJoinNum;
    private Integer id;
    private Integer maxNum;
    private String name;
    private Integer status;
    private String updateTime;
    private Integer userId;
    private UserVO userVO;
}
