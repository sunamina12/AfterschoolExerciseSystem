package com.sy.dao;

import com.sy.pojo.Admin;
import org.apache.ibatis.annotations.Param;

public interface AdminMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Admin record);

    int insertSelective(Admin record);

    Admin selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Admin record);

    int updateByPrimaryKey(Admin record);

    int adminIdExist(int adminId);

    int adminPwdExist(@Param("adminPwd") String adminPwd, @Param("adminId") int adminId);

    Admin selectByAdminId(int adminid);
}