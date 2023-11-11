package com.jx.controller;

import com.jx.common.*;
import com.jx.dao.vo.ListMyCreateTeamsModel;
import com.jx.dao.vo.TeamUserVO;
import com.jx.service.TeamService;
import org.springframework.validation.BindingResult;
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
     *
     * @return
     */
    @GetMapping("/api/team/list")
    public BaseResponse<List<TeamUserVO>> listTeam() {

        List<TeamUserVO> teamUserList = teamService.list();

        return ResultUtils.success(teamUserList);
    }

    /**
     * 退出团队
     *
     * @param teamQuitRequest
     * @return
     */
    @PostMapping("api/team/quit")
    public BaseResponse quitTeam(@RequestBody TeamQuitRequest teamQuitRequest,
                                 HttpSession session) {

        Integer userId = (Integer) session.getAttribute("userId");

        teamService.quitTeam(userId, teamQuitRequest);
        return ResultUtils.success("ok");
    }

    /**
     * <h2>查看我创建的队伍</h2>
     * <hr/>
     * 对我创建的队伍进行数据查看
     *
     * @param listMyCreateTeamsModel 自定义实体类数据
     * @param bindingResult       错误信息
     * @return 返回结构化实体
     * @author 筱锋xiao_lfeng
     */
    @GetMapping("/api/team/list/my/create")
    public BaseResponse listMyCreateTeams(@ModelAttribute ListMyCreateTeamsModel listMyCreateTeamsModel,
                                          HttpSession userInfo, BindingResult bindingResult) {
        if (!bindingResult.hasErrors()) {
            List<TeamUserVO> listMyCreateTeams = teamService.listMyCreateTeams(listMyCreateTeamsModel, userInfo);
            return ResultUtils.success(listMyCreateTeams);
        } else {
            // Forbidden
            return ResultUtils.error(ErrorCode.PARAMS_ERROR);
        }
    }
}
