package com.jx.mapper;

import com.jx.common.UserRegisterRequest;
import com.jx.dao.UserDO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {

    @Insert("insert into project01.user (userAccount,userPassword,planetCode) values " +
            "(#{userAccount},#{userPassword},#{planetCode})")
    void add(UserRegisterRequest userRegisterRequest);

    /**
     * <h2>获取用户</h2>
     * <hr/>
     * 根据用户Id获取用户信息
     *
     * @param userId 用户 Id
     * @return 返回 UserDO
     */
    @Select("SELECT * FROM project01.user WHERE id = #{userId}")
    UserDO selectUserForId(Long userId);
}
