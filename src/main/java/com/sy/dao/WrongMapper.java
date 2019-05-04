package com.sy.dao;

import com.sy.pojo.Wrong;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface WrongMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Wrong record);

    int insertSelective(Wrong record);

    Wrong selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Wrong record);

    int updateByPrimaryKey(Wrong record);

    List selectByStuidAsgnid(@Param("stuid") int stuId, @Param("asgnid") int asgnId);

    int deleteByStuidAsgnid(@Param("stuid") int stuId, @Param("asgnid") int asgnId);

    int getWrongNumByStuid(@Param("stuid") int stuId, @Param("questype") String questype, @Param("status") String status);

    List selectByStuid(int stuid);

    int updateByStuidQuesidSelective(Wrong record);

    int asgnIdExist(int asgnid);

    int finishedNum(int stuid);

    List selectAll();
}