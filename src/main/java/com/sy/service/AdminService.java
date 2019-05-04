package com.sy.service;

import com.sy.pojo.Admin;

/**
 * Created by 板野洋洋 on 2017/3/2.
 */
public interface AdminService {
    public boolean adminPwdExist(String adminPwd, int adminId);
    public boolean adminIdExist(int adminId);
    public Admin selectById(int id);
    public Admin selectByAdminId(int adminid);
    public boolean updateAdminSelective(Admin admin);
}
