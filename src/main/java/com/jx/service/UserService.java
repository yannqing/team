package com.jx.service;

import com.jx.common.UserRegisterRequest;
import com.jx.dao.vo.UserVO;


/**
 * @author lfeng
 */
public interface UserService {


    String register(UserRegisterRequest userRegisterRequest);

    UserVO getCurrentUser(int userId);
}

