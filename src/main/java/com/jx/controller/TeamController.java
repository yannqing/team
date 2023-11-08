package com.jx.controller;

import com.jx.common.BaseResponse;
import com.jx.common.ResultUtils;
import com.jx.common.TeamQuitRequest;
import com.jx.common.TeamUserVO;
import com.jx.service.TeamService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
public class TeamController {

    @Resource
    private TeamService teamService;
    @Resource
    private HttpServletRequest httpServletRequest;

    /**
     * 团队及用户列表查询
     * @return
     */
    @GetMapping("/api/team/list")
    public BaseResponse<List<TeamUserVO>> listTeam(){

        List<TeamUserVO> teamUserList = teamService.list();

        return ResultUtils.success(teamUserList);
    }

    /**
     * 退出团队
     * @param teamQuitRequest
     * @return
     */
    @PostMapping("api/team/quit")
    public BaseResponse quitTeam(@RequestBody TeamQuitRequest teamQuitRequest,
                                 HttpSession session){

        Integer userId = (Integer)session.getAttribute("userId");

        teamService.quitTeam(userId,teamQuitRequest);
        return ResultUtils.success("ok");
    }
}
