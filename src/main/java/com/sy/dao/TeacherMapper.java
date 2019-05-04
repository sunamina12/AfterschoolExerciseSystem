package com.sy.dao;

import com.sy.pojo.Teacher;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TeacherMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Teacher record);

    int insertSelective(Teacher record);

    Teacher selectByPrimaryKey(Integer id);

    List selectAllTeacher();

    int updateByPrimaryKeySelective(Teacher record);

    int updateByPrimaryKey(Teacher record);

    int tchIdExist(int tchId);

    int tchPwdExist(@Param("tchPwd") String tchPwd, @Param("tchId") int tchId);

    Teacher selectByTchId(int tchid);
}