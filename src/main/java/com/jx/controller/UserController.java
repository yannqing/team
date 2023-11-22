package com.jx.controller;

import com.jx.common.BaseResponse;
import com.jx.common.ErrorCode;
import com.jx.common.ResultUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/user")
@RestController
public class UserController {


    @GetMapping("/recommend")
    public BaseResponse recommend(int pageNum, int pageSize){
        return ResultUtils.error(ErrorCode.SUCCESS);
    }
}
