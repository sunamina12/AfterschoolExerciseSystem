package com.sy.dao;

import com.sy.pojo.Blank;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BlankMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Blank record);

    int insertSelective(Blank record);

    Blank selectByPrimaryKey(Integer id);

    List selectAll();

    int updateByPrimaryKeySelective(Blank record);

    int updateByPrimaryKey(Blank record);

    int blkStemExist(String stem);

    int selectMaxId();

    int getQuantity();

    List<String> selectAllSbjt();

    List selectTopicBySbjt(String sbjt);

    int selectQuanByTopic(String sbjt, String topic);

    List selectIdByST(String sbjt, String topic);

    List selectByST(@Param("subj") String subj, @Param("topic") String topic);
}