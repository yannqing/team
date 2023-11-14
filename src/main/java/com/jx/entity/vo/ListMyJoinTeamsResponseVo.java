package com.jx.entity.vo;

import lombok.Data;

/*
        ListMyJoinTeams方法请求的包装类
*/
@Data
public class ListMyJoinTeamsResponseVo {

    private String description;

    private Integer id;

    private Integer[] idList;

    private Integer maxNum;

    private String name;

    private Integer pageNum;

    private Integer pageSize;

    private String searchText;

    private Integer status;

    private Integer userId;

}
