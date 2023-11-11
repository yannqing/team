package com.jx.dao;

import lombok.Getter;
import lombok.Setter;

/**
 * @author 筱锋xiao_lfeng
 * @version v0.0.1
 */
@Getter
@Setter
public class UserTeamDO extends PublicEntityDO {
    private Long userId;
    private Long teamId;
    private String joinTime;
}
