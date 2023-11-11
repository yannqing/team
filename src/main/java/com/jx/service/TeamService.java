package com.jx.service;

import cn.hutool.db.Page;
import com.jx.dao.vo.ListMyCreateTeamsModel;
import com.jx.common.TeamQuitRequest;
import com.jx.dao.vo.ListTeamByPageModel;
import com.jx.dao.vo.TeamUserVO;

import javax.servlet.http.HttpSession;
import java.util.List;

public interface TeamService  {
    List<TeamUserVO> list();

    void quitTeam(Integer userId,TeamQuitRequest teamQuitRequest);

    List<TeamUserVO> listMyCreateTeams(ListMyCreateTeamsModel listMyCreateTeamsModel, HttpSession userInfo);

    Page listTeamsByPage(ListTeamByPageModel listTeamByPageModel, HttpSession userInfo);
}
