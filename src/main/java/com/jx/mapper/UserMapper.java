package com.jx.mapper;

<<<<<<< HEAD
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jx.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {

}
=======
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
>>>>>>> origin/Branch01java8
