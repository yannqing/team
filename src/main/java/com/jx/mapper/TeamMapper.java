package com.jx.mapper;

import com.jx.dao.vo.TeamUserVO;
import com.jx.dao.vo.UserVO;
import com.jx.dao.TeamDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface TeamMapper {

    @Select("select * from team")
    List<TeamUserVO> list();

    @Select("select * from user where id IN (select userId from user_team where teamId = 1)")
    List<UserVO> listUser();

    void quitTeam(Integer userId,Integer teamId);

    /**
     * <h2>筛选创建用户</h2>
     * <hr/>
     * 筛选由 userId 创建的用户
     *
     * @param userId 用户ID
     * @return 返回TeamUserVO的集合类
     */
    @Select("SELECT * FROM project01.team WHERE userId = #{userId}")
    List<TeamDO> getUserCreateTeams(Long userId);
}
