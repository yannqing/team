package com.jx.entity;

import java.util.Date;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
/**
 * 用户队伍关系(UserTeam)表实体类
 *
 * @author zrx
 * @since 2023-11-14 15:54:03
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("user_team")
public class UserTeam  {
    //id
    @TableId
    private Long id;
    //用户id
    private Long userid;
    //队伍id
    private Long teamid;
    //加入时间
    private Date jointime;
    //创建时间
    private Date createtime;
    //修改时间
    private Date updatetime;
    //是否删除
    private Integer isdelete;

    public UserTeam(Long userid, Long teamid) {
        this.userid = userid;
        this.teamid = teamid;
    }
}
