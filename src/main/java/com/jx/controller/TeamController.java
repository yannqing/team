package com.jx.controller;

import com.jx.common.BaseResponse;
import com.jx.entity.vo.ListMyJoinTeamsResponseVo;
import com.jx.entity.vo.TeamJoinRequest;
import com.jx.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

/*
        张睿相   Java
*/
@RestController
@RequestMapping("/api/team")
public class TeamController {

    @Autowired
    private TeamService teamService;

    @PostMapping("/join")
    public BaseResponse join(@RequestBody TeamJoinRequest teamJoinRequest, HttpSession session) {

        BaseResponse result = teamService.joinTeam(teamJoinRequest,session);

        return result;

    }

    @GetMapping("/list/my/join")
    public BaseResponse join(@RequestBody ListMyJoinTeamsResponseVo listMyJoinTeamsResponseVo) {

        BaseResponse result = teamService.ListMyJoinTeam(listMyJoinTeamsResponseVo);

        return result;
    }


}