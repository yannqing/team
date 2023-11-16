package com.jx.service.impl;

import com.jx.common.IsRightAccount;
import com.jx.common.UserRegisterRequest;
import com.jx.dao.UserDO;
import com.jx.dao.vo.UserVO;
import com.jx.mapper.UserMapper;
import com.jx.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;

/**
 * @author lfeng
 */
@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

    @Override
    public String register(UserRegisterRequest userRegisterRequest) {

        //1.两次密码是否正确
        if (!userRegisterRequest.getCheckPassword().
                equals(userRegisterRequest.getUserPassword())) {
            return "两次密码不正确";
        }

        //2.用户名校验
        if (IsRightAccount.existSpecialCharacters(userRegisterRequest)) {
            return "用户名含有特殊字符，不符合规范";
        }

        //3.密码加密
        String password = DigestUtils.md5DigestAsHex
                (userRegisterRequest.getUserPassword().getBytes());
        userRegisterRequest.setUserPassword(password);
        userMapper.add(userRegisterRequest);
        return "ok";
    }

    @Override
    public UserVO getCurrentUser(int userId) {
        // 检查用户是否存在
        UserDO userDO = userMapper.selectUserForId((long) userId);
        if (userDO != null) {
            return new UserVO()
                    .setAvatarUrl(userDO.getAvatarUrl())
                    .setCreatTime(userDO.getCreateTime())
                    .setEmail(userDO.getEmail())
                    .setGender(userDO.getGender())
                    .setId(Math.toIntExact(userDO.getId()))
                    .setPhone(userDO.getPhone())
                    .setPlanetCode(userDO.getPlanetCode())
                    .setProfile(userDO.getProfile())
                    .setTags(userDO.getTags())
                    .setUpdateTime(userDO.getUpdateTime())
                    .setUserAccount(userDO.getUserAccount())
                    .setUserRole(userDO.getUserRole())
                    .setUserStatus(userDO.getUserStats())
                    .setUserName(userDO.getUsername());
        } else {
            return null;
        }
    }
}
