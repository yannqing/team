package com.jx.entity;

import java.util.Date;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
/**
 * (User)表实体类
 *
 * @author zrx
 * @since 2023-11-14 15:18:00
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("user")
public class User  {
    //主键ID@TableId
    private Long id;
    //姓名
    private String username;
    //账号
    private String userAccount;
    //头像
    private String avatarUrl;
    
    private Integer gender;
    //用户简介
    private String profile;
    //密码
    private String userPassword;
    //电话
    private String phone;
    //邮箱
    private String email;
    //标签列表
    private String tags;
    //用户状态，正常
    private Integer userStatus;
    //创建时间
    private Date creatTime;
    //修改时间
    private Date updateTime;
    //逻辑删除
    private Integer isDelete;
    //普通用户-0，管理员-1
    private Integer userRole;
    //星球编号
    private String planetCode;


}
