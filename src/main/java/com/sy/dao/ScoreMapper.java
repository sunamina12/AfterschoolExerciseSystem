package com.sy.dao;

import com.sy.pojo.Score;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ScoreMapper {
    int insert(Score record);

    Score selectByPrimaryKey(Integer id);

    int asgnIfFinished(@Param("stuId")int stuid, @Param("asgnId")int asgnid);

    List selectAll();

    List selectByStuid(int stuid);

    List selectByAsgnid(int asgnid);

    int deleteById(int id);
}
