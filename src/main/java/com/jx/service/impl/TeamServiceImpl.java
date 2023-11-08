package com.jx.service.impl;

import cn.hutool.http.server.HttpServerBase;
import com.jx.common.TeamQuitRequest;
import com.jx.common.TeamUserVO;
import com.jx.common.UserVO;
import com.jx.mapper.TeamMapper;
import com.jx.service.TeamService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
public class TeamServiceImpl implements TeamService {

    @Resource
    private TeamMapper teamMapper;

    @Override
    public List<TeamUserVO> list() {

        //TODO
        List<UserVO> userVOList = teamMapper.listUser();
        List<TeamUserVO> teamUserVOList = teamMapper.list();
        
        return teamUserVOList;
    }

    @Override
    public void quitTeam(Integer userId,TeamQuitRequest teamQuitRequest) {

        teamMapper.quitTeam(userId,teamQuitRequest.getTeamId());
    }


}
