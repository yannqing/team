package com.jx.service.impl;

import com.jx.entity.User;
import com.jx.mapper.UserMapper;
import com.jx.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Schedule {

    @Autowired
    UserMapper userMapper;

    @Autowired
    RedisCache redisCache;

    @Scheduled(cron = "0 0 0 * * ?") // 每天晚上12点触发
    public void schedule(){
        List<User> users = userMapper.selectList(null);
        List<User> allUsers = redisCache.getCacheList("AllUsers");
        if (!users.equals(allUsers)){
            redisCache.deleteObject("AllUsers");
            redisCache.setCacheList("AllUsers",users);
        }
    }

}
