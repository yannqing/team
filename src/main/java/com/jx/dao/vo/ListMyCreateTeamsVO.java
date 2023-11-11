package com.jx.dao.vo;

import lombok.Data;
import org.springframework.lang.Nullable;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import java.util.List;

/**
 * listMyCreateTeams 自定义实体类数据
 *
 * @author 筱锋xiao_lfeng
 * @version v0.0.1
 */
@Data
@Valid
public class ListMyCreateTeamsVO {
    @Nullable
    @Pattern(regexp = "^[0-9A-Z-_]+$")
    private String description;
    @Nullable
    private Long id;
    @Nullable
    private List<Integer> idList;
    @Nullable
    private Integer maxNum;
    @Nullable
    @Pattern(regexp = "^[0-9A-Z-_]+$")
    private String name;
    @Nullable
    private Integer pageNum;
    @Nullable
    private Integer pageSize;
    @Nullable
    @Pattern(regexp = "^[0-9A-Z-_]+$")
    private String searchText;
    @Nullable
    private Integer status;
    @Nullable
    private Long userId;
}
