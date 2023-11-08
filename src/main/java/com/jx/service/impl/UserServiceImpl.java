package com.jx.service.impl;

import com.jx.common.ErrorCode;
import com.jx.common.IsRightAccount;
import com.jx.common.UserRegisterRequest;
import com.jx.exception.BusinessException;
import com.jx.mapper.UserMapper;
import com.jx.service.UserService;
import com.sun.xml.internal.bind.v2.TODO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

    @Override
    public String register(UserRegisterRequest userRegisterRequest) {

        //1.两次密码是否正确
        try {
            if (!userRegisterRequest.getCheckPassword().
                    equals(userRegisterRequest.getUserPassword())) {
                throw new BusinessException(ErrorCode.PARAMS_ERROR, "两次密码不正确");
            }
        }catch (Exception e){
            e.printStackTrace();
            return "两次密码不正确" ;
        }


        //2.用户名校验
        try {
            if (IsRightAccount.existSpecialCharacters(userRegisterRequest)) {
                throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户名不符合规范");
            }
        }catch (Exception e){
            e.printStackTrace();
            return "用户名含有特殊字符，不符合规范";
        }

        //3.密码加密
        String password = DigestUtils.md5DigestAsHex
                (userRegisterRequest.getUserPassword().getBytes());
        userRegisterRequest.setUserPassword(password);
        userMapper.add(userRegisterRequest);
        return "ok";
    }
}
