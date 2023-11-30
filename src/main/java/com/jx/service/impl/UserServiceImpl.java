package com.jx.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jx.entity.User;
import com.jx.service.UserService;
import com.jx.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private RedisCache redisCache;

    /**
     * 使用mybatis-plus对内存中的数据分页处理并返回
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public Page<User> recommend(int pageNum, int pageSize) {
        List<User> users = getUsers();

        // 计算总页数
        int totalSize = users.size();
        int totalPages = (int) Math.ceil((double) totalSize / pageSize);

        // 获取当前页的数据
        int fromIndex = (pageNum - 1) * pageSize;
        int toIndex = Math.min(fromIndex + pageSize, totalSize);
        List<User> currentPageData = users.subList(fromIndex, toIndex);

        // 创建分页对象
        Page<User> page = new Page<>(pageNum, pageSize, totalSize);
        page.setRecords(currentPageData);


        return page;
    }


    /**
     * 从redis中获取数据
     * @return
     */
    public List<User> getUsers(){

        return redisCache.getCacheList("AllUsers");
    }
}
