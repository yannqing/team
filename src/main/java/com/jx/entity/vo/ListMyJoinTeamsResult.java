package com.jx.entity.vo;

import lombok.Data;

import java.util.Date;
import java.util.List;

/*
        张睿相   Java
*/
@Data
public class ListMyJoinTeamsResult {

    //创建时间
    private String createtime;
    //描述
    private String description;
    //过期时间
    private String expiretime;
    //是否参加
    private boolean hasJoin;
    //在队伍的位置
    private Integer hasJoinNum;
    //用户id
    private Long userid;
    //队伍名称
    private String name;
    //最大人数
    private Integer maxnum;
    //0-公开，1–私有，2–加密
    private Integer status;
    //修改时间
    private String updatetime;
    //id@TableId
    private Long id;
    //userVo的集合
    private UserVO userVO;

}
