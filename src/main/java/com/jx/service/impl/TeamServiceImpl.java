package com.jx.service.impl;

import com.jx.common.TeamQuitRequest;
import com.jx.dao.TeamDO;
import com.jx.dao.UserDO;
import com.jx.dao.UserTeamDO;
import com.jx.dao.vo.ListMyCreateTeamsVO;
import com.jx.dao.vo.TeamUserVO;
import com.jx.dao.vo.UserVO;
import com.jx.mapper.TeamMapper;
import com.jx.mapper.UserMapper;
import com.jx.mapper.UserTeamMapper;
import com.jx.service.TeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TeamServiceImpl implements TeamService {

    private final TeamMapper teamMapper;
    private final UserMapper userMapper;
    private final UserTeamMapper userTeamMapper;

    @Override
    public List<TeamUserVO> list() {

        //TODO
        List<UserVO> userVOList = teamMapper.listUser();
        List<TeamUserVO> teamUserVOList = teamMapper.list();

        return teamUserVOList;
    }

    @Override
    public void quitTeam(Integer userId, TeamQuitRequest teamQuitRequest) {

        teamMapper.quitTeam(userId, teamQuitRequest.getTeamId());
    }

    @Override
    public List<TeamUserVO> listMyCreateTeams(ListMyCreateTeamsVO listMyCreateTeamsVO, HttpSession userInfo) {
        // 根据用户信息进行用户查询
        Long userId = (Long) userInfo.getAttribute("userId");
        UserDO userDO = userMapper.selectUserForId(userId);
        UserVO userVO = new UserVO()
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
        // 获取用户信息
        List<TeamDO> teamDO = teamMapper.getUserCreateTeams(userId);
        List<TeamUserVO> teamUserVOList = new ArrayList<>();
        teamDO.forEach(team -> {
            // 获取本人是否加入
            UserTeamDO userTeamDO = userTeamMapper.selectUserTeamForUserIdAndTeamId(userId, team.getUserId());
            // 数据构建
            TeamUserVO teamUserVO = new TeamUserVO();
            teamUserVO
                    .setId(Math.toIntExact(team.getId()))
                    .setName(team.getName())
                    .setDescription(team.getDescription())
                    .setMaxNum(team.getMaxNum())
                    .setExpireTime(team.getExpireTime())
                    .setUserId(Math.toIntExact(team.getUserId()))
                    .setStatus(team.getStatus())
                    .setCreateTime(team.getCreateTime())
                    .setUpdateTime(team.getUpdateTime())
                    .setUserVO(userVO)
                    .setHasJoin(userTeamDO != null)
                    .setHasJoinNum(userTeamMapper.getThisTeamCount(team.getId()));
            // 数据准备完毕
            teamUserVOList.add(teamUserVO);
        });
        return teamUserVOList;
    }


}
