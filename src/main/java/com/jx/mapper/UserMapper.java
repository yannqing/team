package com.jx.mapper;

import com.jx.common.UserRegisterRequest;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {

    @Insert("insert into user (userAccount,userPassword,planetCode) values " +
            "(#{userAccount},#{userPassword},#{planetCode})")
    void add(UserRegisterRequest userRegisterRequest);

}
