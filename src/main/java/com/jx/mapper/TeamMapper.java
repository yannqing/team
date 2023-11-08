package com.jx.mapper;

import com.jx.common.TeamUserVO;
import com.jx.common.UserVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface TeamMapper {

    @Select("select * from team")
    List<TeamUserVO> list();

    @Select("select * from user where id IN (select userId from user_team where teamId = 1)")
    List<UserVO> listUser();

    void quitTeam(Integer userId,Integer teamId);
}
