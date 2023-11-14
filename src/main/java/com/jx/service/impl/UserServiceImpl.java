package com.jx.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jx.common.BaseResponse;
import com.jx.common.ErrorCode;
import com.jx.common.ResultUtils;
import com.jx.constant.UserConstant;
import com.jx.entity.User;
import com.jx.entity.vo.SafetyUser;
import com.jx.entity.vo.UserLoginRequest;
import com.jx.exception.BusinessException;
import com.jx.mapper.UserMapper;
import com.jx.service.UserService;
import com.jx.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;

/**
 * (User)表服务实现类
 *
 * @author zrx
 * @since 2023-11-12 10:16:29
 */
@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private UserMapper userMapper;


    @Override
    public BaseResponse userLogin(UserLoginRequest userLoginRequest, HttpSession session){

        //获取参数userAccount、userPassWord
        String userAccount = userLoginRequest.getUserAccount();
        String userPassword = userLoginRequest.getUserPassword();

        //检测参数是否为空
        BusinessException bException;
        if(userAccount == null){
            bException = new BusinessException(ErrorCode.NULL_ERROR, "userAccount参数为空");
            bException.printStackTrace();
            return ResultUtils.error(ErrorCode.NULL_ERROR, "userAccount参数为空");
        }
        if(userPassword == null){
            bException = new BusinessException(ErrorCode.NULL_ERROR, "userPassword参数为空");
            bException.printStackTrace();
            return ResultUtils.error(ErrorCode.NULL_ERROR, "userPassword参数为空");
        }

        //根据账号密码查询用户id
        LambdaQueryWrapper<User> userWrapper = new LambdaQueryWrapper<>();
        userWrapper.eq(User::getUserAccount,userAccount);
        userWrapper.eq(User::getUserPassword,userPassword);

        //封装vo类
        User user = userMapper.selectOne(userWrapper);

        //如果没有该用户，抛出异常
        if(user == null){
            bException = new BusinessException(ErrorCode.PARAMS_ERROR ,"未找到对应用户");
            bException.printStackTrace();
            return ResultUtils.error(ErrorCode.PARAMS_ERROR, "未找到对应用户");
        }

        SafetyUser userVO = BeanCopyUtils.copyBean(user,SafetyUser.class);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (user.getUpdateTime() != null) {
            userVO.setUpdateTime(simpleDateFormat.format(user.getUpdateTime()));
        }
        if(user.getCreatTime() != null) {
            userVO.setCreatTime(simpleDateFormat.format(user.getCreatTime()));
        }

        if(session.getAttribute(UserConstant.USER_LOGIN_STATE) != null){
            BaseResponse baseResponse = new BaseResponse
                    (0,(User)session.getAttribute(UserConstant.USER_LOGIN_STATE),"用户已登录");
            return baseResponse;
        }

        // 获取当前请求的Session对象，如果不存在则创建新的Session
        session.setAttribute(UserConstant.USER_LOGIN_STATE,user);

        //清除密码等敏感信息
        userVO.setUserPassword("");


        return ResultUtils.success(userVO);
    }


    @Override
    public BaseResponse logOut(HttpSession session){

        //如果session中存在用户登录态则移除，否则返回异常
        if (session.getAttribute(UserConstant.USER_LOGIN_STATE) != null){
            session.removeAttribute(UserConstant.USER_LOGIN_STATE);
            return ResultUtils.success(0);
        }else {
            BusinessException bException = new BusinessException(ErrorCode.PARAMS_ERROR ,"用户未登录");
            bException.printStackTrace();
            return ResultUtils.error(ErrorCode.PARAMS_ERROR, "用户未登录");
        }

    }
}
