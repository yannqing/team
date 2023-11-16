package com.jx.service;

import com.jx.dao.vo.ListMyCreateTeamsModel;
import com.jx.common.TeamQuitRequest;
import com.jx.dao.vo.ListTeamByPageModel;
import com.jx.dao.vo.Page;
import com.jx.dao.vo.TeamUserVO;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author 176yunxuan
 */
public interface TeamService  {
    List<TeamUserVO> list();

    void quitTeam(Integer userId,TeamQuitRequest teamQuitRequest);

    List<TeamUserVO> listMyCreateTeams(ListMyCreateTeamsModel listMyCreateTeamsModel, HttpSession userInfo);

    Page listTeamsByPage(ListTeamByPageModel listTeamByPageModel, HttpSession userInfo);
}
