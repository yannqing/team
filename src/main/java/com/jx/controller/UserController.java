package com.jx.controller;

import com.jx.common.BaseResponse;
import com.jx.common.ResultUtils;
import com.jx.common.UserRegisterRequest;
import com.jx.service.UserService;
import org.omg.PortableInterceptor.SUCCESSFUL;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import static com.jx.common.ErrorCode.SUCCESS;

@RestController
public class UserController {

    @Resource
    private UserService userService;


    /**
     * 用户注册
     * @param userRegisterRequest
     * @return
     */
    @PostMapping("/api/user/register")
    public BaseResponse register(@RequestBody UserRegisterRequest userRegisterRequest){

        String result = userService.register(userRegisterRequest);
        return ResultUtils.success(result);
    }
}
