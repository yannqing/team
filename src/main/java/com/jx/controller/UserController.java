package com.jx.controller;

import com.jx.common.BaseResponse;
import com.jx.entity.vo.UserLoginRequest;
import com.jx.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/*
        张睿相   Java
*/
@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public BaseResponse userLogin(@RequestBody UserLoginRequest userLoginRequest, HttpSession session) {

        BaseResponse result = userService.userLogin(userLoginRequest, session);

        return result;
    }

    @PostMapping("/logout")
    public BaseResponse userLogin(HttpSession session) {

        BaseResponse result = userService.logOut(session);

        return result;
    }


}
