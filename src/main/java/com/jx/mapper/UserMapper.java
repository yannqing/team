package com.jx.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jx.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * (User)表数据库访问层
 *
 * @author zrx
 * @since 2023-11-14 15:18:32
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

}
