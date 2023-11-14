package com.jx.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jx.common.BaseResponse;
import com.jx.entity.User;
import com.jx.entity.vo.UserLoginRequest;

import javax.servlet.http.HttpSession;


/**
 * (User)表服务接口
 *
 * @author zrx
 * @since 2023-11-12 10:16:29
 */
public interface UserService extends IService<User> {

    BaseResponse userLogin(UserLoginRequest userLoginRequest, HttpSession session);

    BaseResponse logOut(HttpSession session);
}

