package com.jx.dao.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author 筱锋xiao_lfeng
 * @version 0.0.1
 */
@Getter
@Setter
@Accessors(chain = true)
public class Page<E> {
    private String countId;
    private Integer current;
    private Integer maxLimit;
    private Boolean optimizeCountSql;
    private OrderItem orders;
    private Integer pages;
    private List<E> records;
    private Boolean searchCount;
    private Integer size;
    private Integer total;
}
