package com.jx.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jx.entity.UserTeam;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户队伍关系(UserTeam)表数据库访问层
 *
 * @author zrx
 * @since 2023-11-14 15:46:57
 */
@Mapper
public interface UserTeamMapper extends BaseMapper<UserTeam> {

}
