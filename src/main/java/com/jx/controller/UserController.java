package com.jx.controller;

<<<<<<< HEAD
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jx.common.BaseResponse;
import com.jx.common.ErrorCode;
import com.jx.common.ResultUtils;
import com.jx.entity.User;
import com.jx.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/user")
@RestController
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/recommend")
    public BaseResponse<Page<User>> recommend(int pageNum, int pageSize){
        Page<User> recommend = userService.recommend(pageNum, pageSize);

        return ResultUtils.success(recommend);
=======
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
>>>>>>> origin/Branch01java8
    }
}
