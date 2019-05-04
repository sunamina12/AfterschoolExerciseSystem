package com.sy.dao;

import com.sy.pojo.AsgnQues;

import java.util.List;

public interface AsgnQuesMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AsgnQues record);

    int insertSelective(AsgnQues record);

    AsgnQues selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AsgnQues record);

    int updateByPrimaryKey(AsgnQues record);

    int delByAsgnId(int asgnid);

    List selectByAsgnid(int asgnid);
}