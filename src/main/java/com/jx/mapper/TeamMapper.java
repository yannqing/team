package com.jx.mapper;

import com.jx.dao.vo.ListMyCreateTeamsModel;
import com.jx.dao.vo.ListTeamByPageModel;
import com.jx.dao.vo.TeamUserVO;
import com.jx.dao.vo.UserVO;
import com.jx.dao.TeamDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author 176yunxuan
 */
@Mapper
public interface TeamMapper {

    @Select("select * from project01.team")
    List<TeamUserVO> list();

    @Select("select * from project01.user where id IN (select userId from project01.user_team where teamId = 1)")
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
    @Select("SET @start = IF(#{pageSize} IS NULL, 0, #{pageSize} * (#{pageNum} - 1));" +
            "SET @limit = IF(#{pageSize} IS NULL, 10, #{pageSize});" +
            "SELECT * FROM project01.team " +
                "WHERE " +
                    "userId = #{userId} " +
                "AND (name = #{name} OR name LIKE '%'#{searchText}'%' OR #{name} IS NULL) " +
                "AND (description = #{description} OR description LIKE '%'#{searchText}'%' OR #{description} IS NULL) " +
                "AND (maxNum = #{maxNum} OR #{maxNum} IS NULL) " +
                "AND (status = #{status} OR #{status} IS NULL) " +
                "AND (userId = #{userId} OR #{userId} IS NULL)" +
                "AND (id IN (#{idList}) OR #{idList} IS NULL) " +
            "ORDER BY id DESC LIMIT @start, @limit;")
    List<TeamDO> getUserCreateTeams(ListMyCreateTeamsModel listMyCreateTeamsModel, Long userId, @Param("idList") List<Integer> idList);

    @Select("SET @start = IF(#{pageSize} IS NULL, 0, #{pageSize} * (#{pageNum} - 1));" +
            "SET @limit = IF(#{pageSize} IS NULL, 10, #{pageSize});" +
            "SELECT * FROM project01.team " +
                "WHERE " +
                    "(description = #{description} OR team.description LIKE '%'#{searchText}'%' OR #{description} IS NULL) " +
                "AND (name = #{name} OR name LIKE '%'#{searchText}'%' OR #{name} IS NULL)" +
                "AND (maxNum = #{maxNum} OR #{maxNum} IS NULL) " +
                "AND (status = #{status} OR #{status} IS NULL) " +
                "AND (userId = #{userId} OR #{userId} IS NULL)" +
                "AND (id IN (#{idList}) OR #{idList} IS NULL) " +
            "ORDER BY id DESC LIMIT @start, @limit;")
    List<TeamDO> listTeamByPage(ListTeamByPageModel listTeamByPageModel, @Param("idList") List<Integer> idList);

}

