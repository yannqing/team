package com.jx.service;

import com.jx.common.UserRegisterRequest;
import org.springframework.stereotype.Service;


public interface UserService {


    String register(UserRegisterRequest userRegisterRequest);
}
