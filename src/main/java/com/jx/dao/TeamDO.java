package com.jx.dao;

import lombok.Getter;
import lombok.Setter;

/**
 * @author 筱锋xiao_lfeng
 * @version v0.0.1
 */
@Getter
@Setter
public class TeamDO extends PublicEntityDO {
    private String name;
    private String description;
    private Integer maxNum;
    private String expireTime;
    private Long userId;
    private Integer status;
    private String password;
}
