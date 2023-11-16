package com.jx.controller;

import com.jx.common.BaseResponse;
import com.jx.common.ErrorCode;
import com.jx.common.ResultUtils;
import com.jx.common.TeamQuitRequest;
import com.jx.dao.vo.ListMyCreateTeamsModel;
import com.jx.dao.vo.ListTeamByPageModel;
import com.jx.dao.vo.Page;
import com.jx.dao.vo.TeamUserVO;
import com.jx.service.TeamService;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("/api/team")
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
    @GetMapping("/list")
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
    @PostMapping("/quit")
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
     * <p/>
     * Forbidden    PARAMS_ERROR<br/>
     * Unauthorized NO_AUTH<br/>
     *
     * @param listMyCreateTeamsModel 自定义实体类数据
     * @param userInfo               获取用户Session
     * @param bindingResult          错误信息
     * @return 返回结构化实体
     * @author 筱锋xiao_lfeng
     */
    @GetMapping("/list/my/create")
    public BaseResponse listMyCreateTeams(@ModelAttribute ListMyCreateTeamsModel listMyCreateTeamsModel,
                                          HttpSession userInfo, BindingResult bindingResult) {
        Integer userId = (Integer) userInfo.getAttribute("userId");
        if (userId != null) {
            if (!bindingResult.hasErrors()) {
                List<TeamUserVO> listMyCreateTeams = teamService.listMyCreateTeams(listMyCreateTeamsModel, userInfo);
                return ResultUtils.success(listMyCreateTeams);
            } else {
                // Forbidden
                return ResultUtils.error(ErrorCode.PARAMS_ERROR);
            }
        } else {
            // Unauthorized
            return ResultUtils.error(ErrorCode.NO_AUTH);
        }
    }

    /**
     * <h2>通过页码查看队伍</h2>
     * <hr/>
     * 对队伍内容进行分页查询
     * <p/>
     * Forbidden    PARAMS_ERROR<br/>
     * Unauthorized NO_AUTH<br/>
     *
     * @param listTeamByPageModel 自定义实体类数据
     * @param userInfo            获取用户Session
     * @param bindingResult       错误信息
     * @return 返回结构化实体
     * @author 筱锋xiao_lfeng
     */
    @GetMapping("/list/page")
    public BaseResponse listTeamsByPage(@ModelAttribute ListTeamByPageModel listTeamByPageModel,
                                        HttpSession userInfo, BindingResult bindingResult) {
        Integer userId = (Integer) userInfo.getAttribute("userId");
        if (userId != null) {
            if (!bindingResult.hasErrors()) {
                Page listTeamsByPage = teamService.listTeamsByPage(listTeamByPageModel, userInfo);
                return ResultUtils.success(listTeamsByPage);
            } else {
                // Forbidden
                return ResultUtils.error(ErrorCode.PARAMS_ERROR);
            }
        } else {
            // Unauthorized
            return ResultUtils.error(ErrorCode.NO_AUTH);
        }
    }
}
