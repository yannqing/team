package com.jx.controller;

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
    }
}
