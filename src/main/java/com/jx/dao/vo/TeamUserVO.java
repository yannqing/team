package com.jx.dao.vo;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author 176yunxuan
 */
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
