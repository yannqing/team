package com.jx.entity;

import java.util.Date;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
/**
 * 标签(Tag)表实体类
 *
 * @author zrx
 * @since 2023-11-14 15:18:00
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("tag")
public class Tag  {
    //主键ID@TableId
    private Long id;
    //标签名称
    private String tagname;
    //用户ID
    private Long userid;
    //父标签ID
    private Long parentid;
    //0-不是父标签, 1-是父标签
    private Integer isparent;
    //创建时间
    private Date createtime;
    //修改时间
    private Date updatetime;
    //逻辑删除
    private Integer isdelete;


}
