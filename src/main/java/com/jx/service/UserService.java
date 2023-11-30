package com.jx.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jx.common.BaseResponse;
import com.jx.common.ErrorCode;
import com.jx.common.ResultUtils;
import com.jx.entity.User;

public interface UserService {
    Page<User> recommend(int pageNum, int pageSize);
}
