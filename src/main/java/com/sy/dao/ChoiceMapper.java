package com.sy.dao;

import com.sy.pojo.Choice;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ChoiceMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Choice record);

    int insertSelective(Choice record);

    Choice selectByPrimaryKey(Integer id);

    List selectAll();

    int updateByPrimaryKeySelective(Choice record);

    int updateByPrimaryKey(Choice record);

    int choStemExist(String stem);

    int selectMaxId();

    int getQuantity();

    List selectNum();

    List<String> selectAllSbjt();

    List selectTopicBySbjt(String sbjt);

    int selectQuanByTopic(String sbjt, String topic);

    List selectIdByST(String sbjt, String topic);

    List selectByST(@Param("sbjt") String sbjt, @Param("topic") String topic);
}