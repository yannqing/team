package com.jx.common;

import com.jx.mapper.UserMapper;
import lombok.Data;
import javax.annotation.Resource;
import java.util.regex.Pattern;


public class IsRightAccount {

    @Resource
    private UserMapper userMapper;

    //判断存在特殊字符
    private static final Pattern SPECIAL_CHARACTER_PATTERN = Pattern.compile("[^a-zA-Z0-9]");
    public static boolean existSpecialCharacters(UserRegisterRequest userRegisterRequest) {
        return SPECIAL_CHARACTER_PATTERN.matcher(userRegisterRequest.getUserAccount()).find();
    }

    public boolean isRightAccount(UserRegisterRequest userRegisterRequest){
        if(existSpecialCharacters(userRegisterRequest)){
            return false;
        }
        return true;
    }
}
