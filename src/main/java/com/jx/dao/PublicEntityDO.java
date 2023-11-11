package com.jx.dao;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * @author 筱锋xiao_lfeng
 * @version v0.0.1
 */
@Getter
@Setter
public class PublicEntityDO {
    private Long id;
    private String createTime;
    private String updateTime;
    private boolean isDelete;
}
