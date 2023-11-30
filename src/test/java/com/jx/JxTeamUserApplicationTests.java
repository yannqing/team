package com.jx;

import com.baomidou.mybatisplus.core.toolkit.Assert;
import com.jx.entity.User;
import com.jx.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.jx.utils.RedisCache;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest
class JxTeamUserApplicationTests {

    @Test
    void contextLoads() {
    }
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private  RedisCache  redisCache;

    @Test
    public void testSelect() {
        System.out.println(("----- selectAll method test ------"));
        List<User> userList = userMapper.selectList(null);
        userList.forEach(System.out::println);
    }

    @Test
    public void testRedis(){
//        redisCache.setCacheObject("love","xxx");
        String love = redisCache.getCacheObject("love");
        System.out.println(love);
    }


}
