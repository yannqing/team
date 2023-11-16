package com.jx.controller;

import com.jx.common.BaseResponse;
import com.jx.common.ErrorCode;
import com.jx.common.ResultUtils;
import com.jx.common.UserRegisterRequest;
import com.jx.dao.vo.UserVO;
import com.jx.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * @author lfeng
 */
@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;


    /**
     * 用户注册
     * @param userRegisterRequest
     * @return
     */
    @PostMapping("/register")
    public BaseResponse register(@RequestBody UserRegisterRequest userRegisterRequest){

        String result = userService.register(userRegisterRequest);
        return ResultUtils.success(result);
    }

    @PostMapping("/current")
    public BaseResponse getCurrentUser(HttpSession session) {
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId != null) {
            UserVO getCurrentUser = userService.getCurrentUser(userId);
            return ResultUtils.success(getCurrentUser);
        } else {
            return ResultUtils.error(ErrorCode.NOT_LOGIN);
        }
    }
}
