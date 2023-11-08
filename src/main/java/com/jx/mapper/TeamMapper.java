package com.jx.mapper;

import com.jx.common.TeamUserVO;
import com.jx.common.UserVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.Arrays;
import java.util.List;

@Mapper
public interface TeamMapper {

    //@Select("select * from team")
    public List<TeamUserVO> list(String description, Integer id, Integer pageNum,
                           Integer pageSize, Integer userId, Integer status, String name, Integer maxNum);

    @Select("select * from team")
    List<TeamUserVO> listTeam();
    @Select("select * from user where id in (select userId from user_team where teamId = #{teamId}) ")
    List<UserVO> listUser(Integer teamId);

    void quitTeam(Integer userId,Integer teamId);
}
