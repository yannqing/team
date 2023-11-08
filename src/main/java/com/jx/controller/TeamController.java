package com.jx.controller;

import com.jx.common.*;
import com.jx.service.TeamService;
import org.springframework.web.bind.annotation.*;

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
    public BaseResponse <List<TeamUserVO>> listTeam(@RequestParam(required = false,defaultValue = "null") String description,
                                                   @RequestParam(required = false,defaultValue = "-1") Integer id, @RequestParam(required = false,defaultValue = "-1") Integer maxNum, @RequestParam(required = false,defaultValue = "null") String name,
                                                   @RequestParam(required = false,defaultValue = "1") Integer pageNum, @RequestParam(required = false,defaultValue = "10") Integer pageSize,
                                                   @RequestParam(required = false,defaultValue = "-1") Integer status, @RequestParam(required = false,defaultValue = "-1") Integer userId) {

        List<TeamUserVO> teamUserList = teamService.list(description,id,pageNum,
                pageSize,userId,status,name,maxNum);

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
