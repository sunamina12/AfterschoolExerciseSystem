package com.sy.service;

import com.sy.pojo.Score;

import java.util.List;

/**
 * Created by yangitano on 4/27/17.
 */
public interface ScoreService {
    public void insert(Score score);

    public boolean asgnIfFinished(int stuid, int asgnid);

    public List selectAll();

    public List selectByStuid(int stuid);

    public List selectByAsgnid(int asgnid);

    public boolean deleteById(int id);

    public Score selectById(int id);
}
