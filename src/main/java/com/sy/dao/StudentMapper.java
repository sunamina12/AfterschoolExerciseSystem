package com.sy.dao;

import com.sy.pojo.Student;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface StudentMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Student record);

    int insertSelective(Student record);

    Student selectByPrimaryKey(Integer id);

    List selectAllStudent();

    int updateByPrimaryKeySelective(Student record);

    int updateByPrimaryKey(Student record);

    int stuIdExist(int stuId);

    int stuPwdExist(@Param("stuPwd") String stuPwd, @Param("stuId") int stuId);

    Student selectByStuId(int stuid);

    List<String> selectAllClassId();
}