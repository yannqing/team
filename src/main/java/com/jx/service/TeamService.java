package com.jx.service;

import com.jx.common.TeamQuitRequest;
import com.jx.common.TeamUserVO;

import java.util.List;

public interface TeamService  {
    List<TeamUserVO> list();

    void quitTeam(Integer userId,TeamQuitRequest teamQuitRequest);
}
