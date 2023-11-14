package com.jx.entity;

import java.util.Date;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
/**
 * 队伍(Team)表实体类
 *
 * @author zrx
 * @since 2023-11-14 15:54:02
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("team")
public class Team  {
    //id
    @TableId
    private Long id;
    //队伍名称
    private String name;
    //描述
    private String description;
    //最大人数
    private Integer maxNum;
    //过期时间
    private Date expireTime;
    //用户id
    private Long userId;
    //0-公开，1–私有，2–加密
    private Integer status;
    //密码
    private String password;
    //创建时间
    private Date createTime;
    //修改时间
    private Date updateTime;
    //是否删除
    private Integer isDelete;


}
