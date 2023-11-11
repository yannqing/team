package com.jx.dao.vo;

import com.jx.dao.TeamDO;
import lombok.Data;

import java.util.List;

/**
 * @author 筱锋xiao_lfeng
 * @version v0.0.1
 */
@Data
public class Team {
    private String countId;
    private Integer current;
    private Integer maxLimit;
    private Boolean optimizeCountSql;
    private List<OrderItem> orders;
    private Integer pages;
    private List<TeamDO> records;
    private Boolean searchCount;
    private Integer size;
    private Integer total;
}
