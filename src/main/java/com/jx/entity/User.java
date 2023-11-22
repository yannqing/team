package com.jx.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.sql.Timestamp;

@Data
@TableName("`user`")
public class User{
    @TableId
    private Integer id;
    private String username;
    @TableField("userAccount")
    private String userAccount;
    @TableField("avatarUrl")
    private String avatarUrl;
    private int gender;
    private String profile;
    @TableField("userPassword")
    private String userPassword;
    private String phone;
    private String email;
    private String tags;
    @TableField("userStatus")
    private int userStatus;
    @TableField("createTime")
    private Timestamp createTime;
    @TableField("updateTime")
    private Timestamp updateTime;
    @TableField("isDelete")
    private int isDelete;
    @TableField("userRole")
    private int userRole;
    @TableField("planetCode")
    private String planetCode;

}
