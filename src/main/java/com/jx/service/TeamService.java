package com.jx.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jx.common.BaseResponse;
import com.jx.entity.Team;
import com.jx.entity.vo.ListMyJoinTeamsResponseVo;
import com.jx.entity.vo.TeamJoinRequest;
import com.jx.entity.vo.UserLoginRequest;

import javax.servlet.http.HttpSession;


/**
 * 队伍(Team)表服务接口
 *
 * @author zrx
 * @since 2023-11-12 10:16:29
 */
public interface TeamService extends IService<Team> {

    BaseResponse joinTeam(TeamJoinRequest teamJoinRequest, HttpSession session);

    BaseResponse ListMyJoinTeam(ListMyJoinTeamsResponseVo listMyJoinTeamsResponseVo);

}

