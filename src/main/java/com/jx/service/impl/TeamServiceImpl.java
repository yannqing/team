package com.jx.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jx.common.BaseResponse;
import com.jx.common.ErrorCode;
import com.jx.common.ResultUtils;
import com.jx.constant.UserConstant;
import com.jx.entity.LockManager;
import com.jx.entity.Team;
import com.jx.entity.User;
import com.jx.entity.UserTeam;
import com.jx.entity.vo.*;
import com.jx.exception.BusinessException;
import com.jx.mapper.TeamMapper;
import com.jx.mapper.UserMapper;
import com.jx.mapper.UserTeamMapper;
import com.jx.service.TeamService;
import com.jx.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;

/**
 * 队伍(Team)表服务实现类
 *
 * @author zrx
 * @since 2023-11-12 10:16:29
 */
@Service("teamService")
public class TeamServiceImpl extends ServiceImpl<TeamMapper, Team> implements TeamService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserTeamMapper userTeamMapper;
    @Autowired
    private TeamMapper teamMapper;
    @Autowired
    private LockManager lockManager;


    @Override
    public BaseResponse joinTeam(TeamJoinRequest teamJoinRequest, HttpSession session) {

        //密码 队伍id
        String password;
        Long teamId;

        //获取参数
        try {
            password = teamJoinRequest.getPassword();
            teamId = teamJoinRequest.getTeamId();
        } catch (BusinessException businessException) {
            businessException = new BusinessException(ErrorCode.PARAMS_ERROR, "参数转换异常");
            businessException.printStackTrace();
            return ResultUtils.error(ErrorCode.PARAMS_ERROR, "参数类型错误");
        }

        BusinessException bException;

        //判断是否为空
        if (password == null) {
            bException = new BusinessException(ErrorCode.NULL_ERROR, "password参数为空");
            bException.printStackTrace();
            return ResultUtils.error(ErrorCode.NULL_ERROR, "password参数为空");
        } else if (teamId == null) {
            bException = new BusinessException(ErrorCode.NULL_ERROR, "teamId参数为空");
            bException.printStackTrace();
            return ResultUtils.error(ErrorCode.NULL_ERROR, "teamID参数为空");
        }

        //加锁，控制只有一个线程能获取到锁
        Lock lock = lockManager.getLock();
        lock.lock();
        try {

            //根据teamId与passWord获取team对象
            LambdaQueryWrapper<Team> teamWrapper = new LambdaQueryWrapper<>();
            teamWrapper.eq(Team::getId,teamId);
            Team team = teamMapper.selectOne(teamWrapper);
            //检测能否根据teamId找到对应队伍
            if(team == null){
                bException = new BusinessException(ErrorCode.PARAMS_ERROR,"未找到对应队伍");
                bException.printStackTrace();
                return ResultUtils.error(ErrorCode.PARAMS_ERROR,"未找到对应队伍");
            }
            teamWrapper.eq(Team::getPassword,password);
            team = teamMapper.selectOne(teamWrapper);
            //检测队伍密码是否正确
            if(team == null){
                bException = new BusinessException(ErrorCode.PARAMS_ERROR,"队伍密码错误");
                bException.printStackTrace();
                return ResultUtils.error(ErrorCode.PARAMS_ERROR,"队伍密码错误");
            }


            //检测用户是否登录，登录则获取用户信息,并把用户加入队伍
            if(session.getAttribute(UserConstant.USER_LOGIN_STATE) != null) {
                //获取用户登录态
                User userVO = (User) session.getAttribute(UserConstant.USER_LOGIN_STATE);

                //检测该用户是否以及加入该队伍
                LambdaQueryWrapper<UserTeam> userTeamWrapper = new LambdaQueryWrapper<>();
                userTeamWrapper.eq(UserTeam::getUserid,userVO.getId()).eq(UserTeam::getTeamid,teamId);
                List<UserTeam> resultData = userTeamMapper.selectList(userTeamWrapper);
                if(resultData.size() != 0){
                    return ResultUtils.success("用户已加入该队伍");
                }

                UserTeam userTeam = new UserTeam(userVO.getId(),teamId);
                userTeamMapper.insert(userTeam);
                return ResultUtils.success(true);
            }else {
                bException = new BusinessException(ErrorCode.NOT_LOGIN);
                bException.printStackTrace();
                return ResultUtils.error(ErrorCode.NOT_LOGIN);
            }

        } finally {
            lock.unlock();
        }

    }

    @Override
    public BaseResponse ListMyJoinTeam(ListMyJoinTeamsResponseVo listMyJoinTeamsResponseVo) {

        //获取用户id,pageNum,pageSize参数这三个必要参数
        Integer userId = listMyJoinTeamsResponseVo.getUserId();
        Integer pageNum = listMyJoinTeamsResponseVo.getPageNum();
        Integer pageSize = listMyJoinTeamsResponseVo.getPageSize();
        //检查是否为空
        BusinessException bException;
        if (userId == null) {
            bException = new BusinessException(ErrorCode.NULL_ERROR, "userId参数为空");
            bException.printStackTrace();
            return ResultUtils.error(ErrorCode.NULL_ERROR, "userId参数为空");
        }
        if(pageNum == null){
            bException = new BusinessException(ErrorCode.NULL_ERROR, "pageNum参数为空");
            bException.printStackTrace();
            return ResultUtils.error(ErrorCode.NULL_ERROR, "pageNum参数为空");
        }
        if(pageSize == null){
            bException = new BusinessException(ErrorCode.NULL_ERROR, "pageSize参数为空");
            bException.printStackTrace();
            return ResultUtils.error(ErrorCode.NULL_ERROR, "pageSize参数为空");
        }


        //先根据用户id查询队伍id
        List<Long> teamIds = new ArrayList<>();
        LambdaQueryWrapper<UserTeam> userTeamWrapper = new LambdaQueryWrapper<>();
        List<UserTeam> userTeams = userTeamMapper.selectList(userTeamWrapper.eq(UserTeam::getUserid, userId));
        for (UserTeam userTeam : userTeams) {
            teamIds.add(userTeam.getTeamid());
        }

        //再根据searchText查询对应队伍id,并进行筛选
        String searchText = listMyJoinTeamsResponseVo.getSearchText();
        //如果搜索词存在
        if (searchText != null && !searchText.equals("")) {
            for (Long teamId : teamIds) {
                Team team = teamMapper.selectById(teamId);
                String teamDescription = team.getDescription();
                //如果不包含搜索词，则在储存teamId的数组里移除该teamId
                if (!teamDescription.contains(searchText)) {
                    teamIds.remove(teamId);
                }
            }
        }

        //再根据name查询对应队伍id,并进行筛选
        String name = listMyJoinTeamsResponseVo.getName();
        if(name != null && !name.equals("")){
            for (Long teamId : teamIds) {
                Team team = teamMapper.selectById(teamId);
                String teamName = team.getName();
                //如果不包含name，则在储存teamId的数组里移除该teamId
                if (!name.equals(teamName)) {
                    teamIds.remove(teamId);
                }
            }
        }

        //再根据status查询对应队伍id,并进行筛选
        Integer status = listMyJoinTeamsResponseVo.getStatus();
        if(status != null){
            for (Long teamId : teamIds) {
                Team team = teamMapper.selectById(teamId);
                Integer teamStatus = team.getStatus();
                //如果状态值不相等，则在储存teamId的数组里移除该teamId
                if (!(teamStatus==status)) {
                    teamIds.remove(teamId);
                }
            }
        }


        //再根据description查询，并筛选队伍id
        String description = listMyJoinTeamsResponseVo.getDescription();

        //再根据id,与idList添加队伍id
        Integer needAddTeamId = listMyJoinTeamsResponseVo.getId();
        if (needAddTeamId != null && needAddTeamId > 0) {
            teamIds.add(Long.valueOf(needAddTeamId));
        }
        Integer[] needAddTeamIdList = listMyJoinTeamsResponseVo.getIdList();
        if (needAddTeamIdList != null) {
            for (Integer addTeamId : needAddTeamIdList) {
                teamIds.add(Long.valueOf(addTeamId));
            }
        }

        //查询对应用户信息
        User user = userMapper.selectById(userId);

        //开启分页查询,获取查询结果
        Page<Team> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<Team> teamWrapper = new LambdaQueryWrapper<>();
        teamWrapper.in(Team::getId,teamIds);
        List<Team> teams = teamMapper.selectPage(page,teamWrapper).getRecords();
        //封装vo类
        List<ListMyJoinTeamsResult> resultData = new ArrayList<>();
        resultData = BeanCopyUtils.copyBeanList(teams,ListMyJoinTeamsResult.class);
        UserVO userVO = BeanCopyUtils.copyBean(user,UserVO.class);


        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (user.getUpdateTime() != null) {
            userVO.setUpdateTime(simpleDateFormat.format(user.getUpdateTime()));
        }
        if(user.getCreatTime() != null) {
            userVO.setCreatTime(simpleDateFormat.format(user.getCreatTime()));
        }

        userVO.setProfile(user.getProfile());


        for(int i = 0;i < resultData.size(); i++){
            ListMyJoinTeamsResult result = resultData.get(i);
            //设置类型不同的字段
            if(teams.get(i).getCreateTime()!=null) {
                result.setCreatetime(simpleDateFormat.format(teams.get(i).getCreateTime()));
            }
            if(teams.get(i).getUpdateTime()!=null) {
                result.setUpdatetime(simpleDateFormat.format(teams.get(i).getUpdateTime()));
            }
            if(teams.get(i).getExpireTime()!=null) {
                result.setExpiretime(simpleDateFormat.format(teams.get(i).getExpireTime()));
            }
            result.setHasJoin(true);
            result.setHasJoinNum(0);
            result.setUserVO(userVO);
        }

        //如果有description ，设置description
        if(description != null && !description.equals("")){
            BaseResponse baseResponse = new BaseResponse(0,resultData,"",description);
            return baseResponse;
        }

        return ResultUtils.success(resultData);
    }





    

}
