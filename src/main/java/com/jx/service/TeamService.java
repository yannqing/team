package com.jx.service;

import com.jx.dao.vo.ListMyCreateTeamsVO;
import com.jx.common.TeamQuitRequest;
import com.jx.dao.vo.TeamUserVO;
import org.springframework.http.HttpStatus;

import javax.servlet.http.HttpSession;
import java.util.List;

public interface TeamService  {
    List<TeamUserVO> list();

    void quitTeam(Integer userId,TeamQuitRequest teamQuitRequest);

    List<TeamUserVO> listMyCreateTeams(ListMyCreateTeamsVO listMyCreateTeamsVO, HttpSession userInfo);
}
