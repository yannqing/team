package com.jx.service;

import com.jx.common.TeamQuitRequest;
import com.jx.common.TeamUserVO;

import java.util.List;

public interface TeamService  {
    List<TeamUserVO> list(String description, Integer id, Integer pageNum,
                          Integer pageSize, Integer userId, Integer status, String name, Integer maxNum);

    void quitTeam(Integer userId,TeamQuitRequest teamQuitRequest);
}
