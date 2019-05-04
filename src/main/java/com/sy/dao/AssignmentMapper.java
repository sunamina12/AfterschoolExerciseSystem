package com.sy.dao;

import com.sy.pojo.Assignment;

import java.util.List;

public interface AssignmentMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Assignment record);

    int insertSelective(Assignment record);

    Assignment selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Assignment record);

    int updateByPrimaryKey(Assignment record);

    List selectAll();

    List selectByUploader(int id);

    List selectByClassId(int id);

    int asgnIdExist(int asgnid);

    List selectAsgnByUploader(String uploader);

    Assignment selectByAsgnid(int asgnid);
}