package com.sy.service.impl;


/**
 * Created by 板野洋洋 on 2017/3/2.
 */

import com.sy.dao.AdminMapper;
import com.sy.service.AdminService;
import org.springframework.stereotype.Service;
import com.sy.pojo.Admin;

import javax.annotation.Resource;

@Service("adminService")
public class AdminServiceImpl implements AdminService {
    @Resource
    private AdminMapper adminDao;
    //检查管理员号是否存在
    @Override
    public boolean adminIdExist(int adminId){
        if(adminDao.adminIdExist(adminId)>0){
            return false;
        }else{
            return true;
        }
    }
    //检查密码是否正确
    @Override
    public boolean adminPwdExist(String adminPwd, int adminId){
        if(adminDao.adminPwdExist(adminPwd,adminId)>0){
            return true;
        }else{
            return false;
        }
    }
    //查找admin信息
    @Override
    public Admin selectById(int id){ return this.adminDao.selectByPrimaryKey(id); }

    @Override
    public Admin selectByAdminId(int adminid){ return this.adminDao.selectByAdminId(adminid); }

    @Override
    public boolean updateAdminSelective(Admin admin) {
        if (adminDao.updateByPrimaryKeySelective(admin)>0){
            return true;
        } else {
            return false;
        }
    }
}
