package com.jx.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jx.entity.Tag;
import org.apache.ibatis.annotations.Mapper;

/**
 * 标签(Tag)表数据库访问层
 *
 * @author zrx
 * @since 2023-11-14 15:46:54
 */
@Mapper
public interface TagMapper extends BaseMapper<Tag> {

}
