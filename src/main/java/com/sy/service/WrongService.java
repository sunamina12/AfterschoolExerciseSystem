package com.sy.service;

import com.sy.pojo.Wrong;

import java.util.List;

/**
 * Created by yangitano on 4/29/17.
 */
public interface WrongService {
    public void addWrong(Wrong wrong);
    public List selectByStuidAsgnid(int stuId, int asgnId);
    public boolean deleteByStuidAsgnid(int stuId, int asgnId);
    public int getWrongNumByStuid(int stuid, String questype, String status);
    public List selectByStuid(int stuid);
    public int updateByStuidQuesidSelective(Wrong record);
    public boolean asgnIdExist(int asgnid);
    public int finishedNum(int stuid);
    public List selectAll();
}
