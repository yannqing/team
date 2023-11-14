package com.jx.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jx.entity.Team;
import org.apache.ibatis.annotations.Mapper;

/**
 * 队伍(Team)表数据库访问层
 *
 * @author zrx
 * @since 2023-11-14 15:46:55
 */
@Mapper
public interface TeamMapper extends BaseMapper<Team> {

}
