package com.jx.mapper;

import com.jx.dao.UserTeamDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author 筱锋xiao_lfeng
 */
@Mapper
public interface UserTeamMapper {
    /**
     * <h2>获取信息</h2>
     * <hr/>
     * 根据队伍获取信息
     *
     * @param teamId 队伍Id
     * @return 返回队伍信息
     */
    @Select("SELECT * FROM project01.user_team WHERE teamId = #{teamId}")
    List<UserTeamDO> selectThisTeamUsers(Long teamId);

    /**
     * <h2>获取信息</h2>
     * <hr/>
     * 根据用户获取信息
     *
     * @param userId 用户Id
     * @return 返回用户参加的队伍信息
     */
    @Select("SELECT * FROM project01.user_team WHERE userId = #{userId}")
    List<UserTeamDO> selectTeamsForUser(Long userId);

    @Select("SELECT * FROM project01.user_team WHERE userId = #{userId} AND teamId = #{teamId}")
    UserTeamDO selectUserTeamForUserIdAndTeamId(Long userId, Long teamId);

    /**
     * <h2>获取信息</h2>
     * <hr/>
     * 获取队伍中的人数信息
     *
     * @param teamId 队伍Id
     * @return 返回这个队伍人数
     */
    @Select("SELECT COUNT(id) FROM project01.user_team WHERE teamId = #{teamId}")
    Integer getThisTeamCount(Long teamId);
}
