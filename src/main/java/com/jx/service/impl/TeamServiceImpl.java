package com.jx.service.impl;

import com.jx.common.TeamQuitRequest;
import com.jx.common.TeamUserVO;
import com.jx.common.UserVO;
import com.jx.mapper.TeamMapper;
import com.jx.service.TeamService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class TeamServiceImpl implements TeamService {

    @Resource
    private TeamMapper teamMapper;

    @Override
    public List<TeamUserVO> list(String description, Integer id,
                                 Integer pageNum, Integer pageSize, Integer userId,
                                 Integer status, String name, Integer maxNum) {

        //查询所有团队
        List<TeamUserVO> teamUserVOS = teamMapper.list(description, id, pageNum,
                pageSize,userId, status, name, maxNum);

        //封装结果
        List<TeamUserVO> teamUserVOList = new ArrayList<>();
        for(TeamUserVO teamUserVO:teamUserVOS) {
            List<UserVO> userVOS = teamMapper.listUser(teamUserVO.getId());
            TeamUserVO teamUserVO1 = new TeamUserVO();
            teamUserVO1.setUserId(teamUserVO.getUserId());
            teamUserVO1.setCreateTime(teamUserVO.getCreateTime());
            teamUserVO1.setDescription(teamUserVO.getDescription());
            teamUserVO1.setExpireTime(teamUserVO.getExpireTime());
            teamUserVO1.setHasJoinNum(teamUserVO.getHasJoinNum());
            teamUserVO1.setHasJoin(teamUserVO.getHasJoin());
            teamUserVO1.setId(teamUserVO.getId());
            teamUserVO1.setName(teamUserVO.getName());
            teamUserVO1.setMaxNum(teamUserVO.getMaxNum());
            teamUserVO1.setStatus(teamUserVO.getStatus());
            teamUserVO1.setUpdateTime(teamUserVO.getUpdateTime());
            teamUserVO1.setUserVO(userVOS);
            teamUserVOList.add(teamUserVO1);
        }
        //分页返回
        int start = (pageNum-1) * pageSize;
        int end = start + pageSize;
        List<TeamUserVO> pageData = teamUserVOList.subList(start,
                Math.min(end,teamUserVOList.size()));
        return pageData;
        //return teamUserVOList;

    }

    @Override
    public void quitTeam(Integer userId,TeamQuitRequest teamQuitRequest) {

        teamMapper.quitTeam(userId,teamQuitRequest.getTeamId());
    }


}
